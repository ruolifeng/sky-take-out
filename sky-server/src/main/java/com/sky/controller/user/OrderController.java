package com.sky.controller.user;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 07/04/2024 4:34 PM
 */
@Api(tags = "用户端订单相关接口")
@RestController("userOrderController")
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO submitDTO){
        OrderSubmitVO submitVO =orderService.submitOrder(submitDTO);
        return Result.success(submitVO);
    }

    @ApiOperation(value = "客户催单")
    // 客户催单
    @GetMapping("/reminder/{id}")
    public Result remind(@PathVariable Long id) {
        orderService.remind(id);
        return Result.success();
    }
}
