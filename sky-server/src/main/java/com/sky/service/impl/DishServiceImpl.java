package com.sky.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import com.sky.service.DishService;
import com.sky.service.FlavorService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 10:14 AM
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource
    FlavorService flavorService;
    @Resource
    DishMapper dishMapper;

    @Resource
    CategoryService categoryService;

    @Resource
    StemealDishServiceImpl stemealDishService;

    @Transactional
    @Override
    public void saveDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setCategoryId(BaseContext.getCurrentId());
        dish.setCreateTime(LocalDateTime.now());
        this.save(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (!Objects.isNull(flavors) && !flavors.isEmpty()) {
            flavors.forEach(item -> {
                item.setDishId(dish.getId());
                flavorService.save(item);
            });
        }
    }

    @Transactional
    @Override
    public PageResult getPage(DishPageQueryDTO dishPageQueryDTO) {
        // TODO 无法拼接string类型查询
        Page<Dish> paginate = dishMapper.paginate(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize(), QueryWrapper.create()
                .select()
                .from(Dish.class)
                .where(Dish::getCategoryId)
                .eq(dishPageQueryDTO.getCategoryId())
                .eq(Dish::getStatus, dishPageQueryDTO.getStatus())
                .eq(Dish::getName, dishPageQueryDTO.getName()));
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish record : paginate.getRecords()) {
            DishVO dishVO = new DishVO();
            Category category = categoryService.getById(record.getCategoryId());
            BeanUtils.copyProperties(record, dishVO);
            dishVO.setCategoryName(category.getName());
            dishVOList.add(dishVO);
        }
        return new PageResult(paginate.getTotalPage(), dishVOList);
    }

    @Transactional
    @Override
    public void delete(Long[] ids) {
        // 判断当前菜品是否可以删除
        Arrays.stream(ids).forEach(item->{
            Dish dish = dishMapper.selectOneById(item);
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        // 判断当前菜品有没有关联的套餐
        List<SetmealDish> setmealDishes = stemealDishService.getSetmealDishByDishIds(ids);
        if (!Objects.isNull(setmealDishes) && !setmealDishes.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 批量删除菜品
        dishMapper.deleteBatchByIds(Arrays.asList(ids),ids.length);
        dishMapper.BatchDelete(ids);
        // 删除菜品关联的口味
        flavorService.removeByIds(Arrays.asList(ids));
    }

    @Override
    public DishVO getDishAndFlavorsById(Long id) {
        DishVO dishVO = new DishVO();
        // 根据id查询出菜品
        Dish dish = dishMapper.selectOneById(id);
        if (!Objects.isNull(dish)){
            // 查询出菜品关联的口味
            List<DishFlavor> dishFlavors = flavorService.list(QueryWrapper.create().select().from(DishFlavor.class).where(DishFlavor::getDishId).eq(id));
            BeanUtils.copyProperties(dish,dishVO);
            dishVO.setFlavors(dishFlavors);
            return dishVO;
        }
        return null;
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setUpdateTime(LocalDateTime.now());
        this.updateById(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (!Objects.isNull(flavors) && !flavors.isEmpty()){
            // 先删除原来的口味
            flavorService.remove(QueryWrapper.create().where(DishFlavor::getDishId).eq(dish.getId()));
            // 重新插入新的口味
            flavors.forEach(item->{
                item.setDishId(dish.getId());
                flavorService.save(item);
            });
        }
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dish.setUpdateTime(LocalDateTime.now());
        this.updateById(dish);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = this.list(QueryWrapper.create().select().from(Dish.class).where(Dish::getCategoryId).eq(dish.getCategoryId()).eq(Dish::getStatus, StatusConstant.ENABLE));
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish record : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(record,dishVO);
            List<DishFlavor> dishFlavors = flavorService.list(QueryWrapper.create().select().from(DishFlavor.class).where(DishFlavor::getDishId).eq(record.getId()));
            dishVO.setFlavors(dishFlavors);
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }
}
