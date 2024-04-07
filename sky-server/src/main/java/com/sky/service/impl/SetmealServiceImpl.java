package com.sky.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.service.StemealDishService;
import com.sky.vo.DishItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 套餐业务实现
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private StemealDishService setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.selectListByQuery(QueryWrapper.create()
                .eq(Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(Setmeal::getStatus, setmeal.getStatus()));
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        List<Setmeal> setmeals = setmealMapper.selectListByQuery(QueryWrapper.create().eq(Setmeal::getId, id));
        if (setmeals.isEmpty()) {
            return null;
        }else {
            List<SetmealDish> setmealDishes = setmealDishMapper.list(QueryWrapper.create().eq(SetmealDish::getSetmealId, id));
            List<DishItemVO> dishItemVOS = new ArrayList<>();
            for (SetmealDish setmealDish : setmealDishes) {
                Dish dish = dishMapper.selectOneById(setmealDish.getDishId());
                DishItemVO dishItemVO = new DishItemVO();
                BeanUtils.copyProperties(dish, dishItemVO);
                dishItemVOS.add(dishItemVO);
            }
            return dishItemVOS;
        }
    }
}
