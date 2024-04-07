package com.sky.service;

import com.mybatisflex.core.service.IService;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 10:14 AM
 */
public interface DishService extends IService<Dish> {
    void saveDish(DishDTO dishDTO);

    PageResult getPage(DishPageQueryDTO dishPageQueryDTO);

    void delete(Long[] ids);

    DishVO getDishAndFlavorsById(Long id);

    void updateDish(DishDTO dishDTO);

    void updateStatus(Long id, Integer status);

    List<DishVO> listWithFlavor(Dish dish);
}
