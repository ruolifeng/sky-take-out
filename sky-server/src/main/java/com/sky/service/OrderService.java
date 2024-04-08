package com.sky.service;

import com.mybatisflex.core.service.IService;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderSubmitVO;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 07/04/2024 4:38 PM
 */
public interface OrderService extends IService<Orders> {
    OrderSubmitVO submitOrder(OrdersSubmitDTO submitDTO);

    void remind(Long id);
}
