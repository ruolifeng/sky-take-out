package com.sky.service;

import com.mybatisflex.core.service.IService;
import com.sky.entity.SetmealDish;

import java.util.List;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 12:20 PM
 */
public interface StemealDishService extends IService<SetmealDish> {
    List<SetmealDish> getSetmealDishByDishIds(Long[] ids);
}
