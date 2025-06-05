package com.zhang.mapper;

import com.zhang.entity.Recommendation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 推荐结果表Mapper接口
 */
@Mapper
public interface RecommendationMapper {
    void insert(Recommendation recommendation);
    void deleteByUserId(Integer userId);
    List<Recommendation> selectByUserId(Integer userId);
}