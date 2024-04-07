package com.sky.service;

import com.mybatisflex.core.service.IService;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 07/04/2024 11:55 AM
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    void addCart(ShoppingCartDTO shoppingCartDTO);
}
