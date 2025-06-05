package com.zhang.mapper;

import com.zhang.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 电影表Mapper接口
 */
@Mapper
public interface MovieMapper {
    List<Movie> selectAll();
    Movie selectById(Integer id);
}