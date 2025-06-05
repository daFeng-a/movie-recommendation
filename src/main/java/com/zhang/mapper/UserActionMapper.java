package com.zhang.mapper;

import com.zhang.entity.UserAction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户行为表Mapper接口
 */
@Mapper
public interface UserActionMapper {
    void insert(@Param("userId") Integer userId, @Param("movieId") Integer movieId, @Param("actionType") String actionType);
    List<UserAction> selectByUserId(@Param("userId") Integer userId);
}