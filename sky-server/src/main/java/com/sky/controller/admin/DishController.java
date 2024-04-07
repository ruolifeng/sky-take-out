package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.Set;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 10:12 AM
 */
@Api(tags = "菜品接口")
@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Resource
    DishService dishService;

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value = "新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        // 清除redis中的缓存数据
        redisTemplate.delete("dishList:" + dishDTO.getCategoryId());
        dishService.saveDish(dishDTO);
        return Result.success();
    }

    @ApiOperation(value = "分页查询菜品")
    @GetMapping("/page")
    public Result<PageResult> getPage(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.getPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation(value = "删除菜品")
    @DeleteMapping
    public Result delete(@RequestParam Long[] ids){
        // 清除redis中的所有缓存数据
        Set<String> dishList = redisTemplate.keys("dishList:*");
        assert dishList != null;
        redisTemplate.delete(dishList);
        dishService.delete(ids);
        return Result.success();
    }

    @ApiOperation(value = "根据ID查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> get(@PathVariable Long id){
        return Result.success(dishService.getDishAndFlavorsById(id));
    }

    @ApiOperation(value = "更新菜品")
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){
        // 清除redis中的所有缓存数据
        Set<String> dishList = redisTemplate.keys("dishList:*");
        assert dishList != null;
        redisTemplate.delete(dishList);
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    @ApiOperation(value = "更新菜品状态")
    @PostMapping("/status")
    public Result updateStatus(@PathVariable Integer status, @PathParam("id") Long id){
        // 清除redis中的所有缓存数据
        Set<String> dishList = redisTemplate.keys("dishList:*");
        assert dishList != null;
        redisTemplate.delete(dishList);
        dishService.updateStatus(id,status);
        return Result.success();
    }
}
