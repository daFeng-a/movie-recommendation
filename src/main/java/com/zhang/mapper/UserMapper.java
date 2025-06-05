package com.zhang.mapper;

import com.zhang.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表Mapper接口
 */
@Mapper
public interface UserMapper {
    User selectById(Integer id);
    User selectByUsername(@Param("username") String username);
    void insert(User user);
}