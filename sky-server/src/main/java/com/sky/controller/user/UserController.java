package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 3:36 PM
 */
@RestController
@Api(tags = "用户接口")
@RequestMapping("user/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JwtProperties jwtProperties;
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        User user = userService.wxlogin(userLoginDTO);
        Map<String,Object> map = new HashMap<>();
        map.put(JwtClaimsConstant.USER_ID,user.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(),map);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .token(jwt)
                .openid(user.getOpenid())
                .build();
        return Result.success(userLoginVO);
    }
}
