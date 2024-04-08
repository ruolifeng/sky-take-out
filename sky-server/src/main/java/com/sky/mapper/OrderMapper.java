package com.sky.mapper;

import com.mybatisflex.core.BaseMapper;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 07/04/2024 4:38 PM
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    @Select("SELECT * FROM orders WHERE status = #{pendingPayment} AND order_time <#{now}")
    List<Orders> getByStatusAndOrderTimeLt(Integer pendingPayment, LocalDateTime now);
    @Select("select sum(orders.amount) from orders where order_time between #{map.begin} and #{map.end} and status = #{map.status}")
    Double sumByMap(Map map);
}
