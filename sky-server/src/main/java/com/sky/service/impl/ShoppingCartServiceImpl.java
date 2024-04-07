package com.sky.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 07/04/2024 11:56 AM
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void addCart(ShoppingCartDTO shoppingCartDTO) {
        // TODO 操作字符串出现问题
        System.out.println(shoppingCartDTO);
        // 判断当前添加的商品是否存在购物车中，不同的用户有属于自己的购物车
        QueryWrapper wrapper = QueryWrapper.create()
                .select()
                .from(ShoppingCart.class)
                .where(ShoppingCart::getUserId).eq(BaseContext.getCurrentId())
                .and(ShoppingCart::getDishId).eq(shoppingCartDTO.getDishId())
                .and(ShoppingCart::getSetmealId).eq(shoppingCartDTO.getSetmealId());
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectListByQuery(wrapper);
        // 存在，将数量加一
        if (!shoppingCartList.isEmpty()) {
            ShoppingCart shoppingCart = shoppingCartList.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateByQuery(shoppingCart, QueryWrapper.create()
                    .eq(ShoppingCart::getId, shoppingCart.getId()));
        }
        // 不存在，将数据插入
        else {
            ShoppingCart shoppingCart = new ShoppingCart();
            // 判断本次添加的是菜品还是套餐
            if (shoppingCartDTO.getDishId() != null){
                // 菜品
                Dish dish = dishMapper.selectOneById(shoppingCartDTO.getDishId());
                String image = dish.getImage();
                String name = dish.getName();
                BigDecimal price = dish.getPrice();
                shoppingCart.setName(name);
                shoppingCart.setImage(image);
                shoppingCart.setAmount(price);
                shoppingCart.setNumber(1);
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setDishId(shoppingCartDTO.getDishId());
                shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());
            }else {
                // 套餐
                Setmeal setmeal = setmealMapper.selectOneById(shoppingCartDTO.getSetmealId());
                String image = setmeal.getImage();
                String name = setmeal.getName();
                BigDecimal price = setmeal.getPrice();
                shoppingCart.setName(name);
                shoppingCart.setImage(image);
                shoppingCart.setAmount(price);
                shoppingCart.setNumber(1);
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setSetmealId(shoppingCartDTO.getSetmealId());
                shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());
            }
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        return shoppingCartMapper.selectListByQuery(QueryWrapper.create()
                .select()
                .from(ShoppingCart.class)
                .eq(ShoppingCart::getUserId, BaseContext.getCurrentId()));
    }
}
