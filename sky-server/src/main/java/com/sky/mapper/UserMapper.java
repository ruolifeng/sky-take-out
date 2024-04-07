package com.sky.mapper;

import com.mybatisflex.core.BaseMapper;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 3:39 PM
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
