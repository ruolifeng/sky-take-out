package com.sky.mapper;

import com.github.pagehelper.Page;
import com.mybatisflex.core.BaseMapper;
import com.sky.enumeration.OperationType;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
