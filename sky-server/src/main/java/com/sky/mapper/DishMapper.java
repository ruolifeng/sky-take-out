package com.sky.mapper;

import com.mybatisflex.core.BaseMapper;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    void BatchDelete(Long[] ids);
}
