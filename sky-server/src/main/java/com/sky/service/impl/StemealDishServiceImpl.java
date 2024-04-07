package com.sky.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sky.entity.SetmealDish;
import com.sky.mapper.StemealDishMapper;
import com.sky.service.StemealDishService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 12:21 PM
 */
@Service
public class StemealDishServiceImpl extends ServiceImpl<StemealDishMapper, SetmealDish> implements StemealDishService {
    @Override
    public List<SetmealDish> getSetmealDishByDishIds(Long[] ids) {
        return this.listByIds(Arrays.stream(ids).toList());
    }
}
