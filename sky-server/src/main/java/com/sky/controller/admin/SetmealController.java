package com.sky.controller.admin;

import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 07/04/2024 11:54 AM
 */
@Api(tags = "套餐管理")
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @ApiOperation("新增套餐")
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setCreateUser(BaseContext.getCurrentId());
        setmealService.save(setmeal);
        return Result.success();
    }


    @ApiOperation("分页查询套餐")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealDTO) {
        return Result.success(setmealService.pageQuery(setmealDTO));
    }

    // 批量删除
    @ApiOperation("批量删除套餐")
    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result delete(@RequestBody Long[] ids) {
        setmealService.removeByIds(Arrays.stream(ids).toList());
        return Result.success();
    }

    //根据id查询
    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<Setmeal> get(@PathVariable("id") Long id) {
        return Result.success(setmealService.getById(id));
    }

    // 套餐起售停售
    @ApiOperation("套餐起售停售")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result status(@PathVariable Integer status,Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setId(id);
        setmeal.setStatus(status);
        return Result.success();
    }
}
