package com.zhang.mapper;

import com.zhang.entity.Rating;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 评分表Mapper
 */
@Mapper
public interface RatingMapper {
    List<Rating> selectAll();
    List<Rating> selectByUserId(Integer userId);
    List<Rating> selectByMovieId(Integer movieId);
    void insert(Rating rating);
}