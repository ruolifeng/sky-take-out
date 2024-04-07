package com.sky.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavordMapper;
import com.sky.service.FlavorService;
import org.springframework.stereotype.Service;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 10:20 AM
 */
@Service
public class FlavorServiceImpl extends ServiceImpl<DishFlavordMapper,DishFlavor> implements FlavorService {
}
