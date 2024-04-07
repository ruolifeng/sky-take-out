package com.sky.service;

import com.mybatisflex.core.service.IService;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 3:38 PM
 */
public interface UserService extends IService<User> {
    User wxlogin(UserLoginDTO userLoginDTO);
}
