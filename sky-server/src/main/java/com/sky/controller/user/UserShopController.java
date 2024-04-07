package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 2:16 PM
 */
@Api(tags = "店铺状态")
@RestController
@RequestMapping("/user/shop")
public class UserShopController {
    private static final String SHOP_STATUS = "SHOP_STATUS";
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @ApiOperation("获取店铺状态")
    @GetMapping("/status")
    public Result getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        return Result.success(status);
    }
}
