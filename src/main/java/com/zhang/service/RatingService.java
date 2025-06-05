package com.zhang.service;

import com.zhang.entity.Rating;

/**
 * 评分相关服务接口
 */
public interface RatingService {
    /**
     * 新增评分，并自动更新该用户推荐
     */
    void addRating(Rating rating);
}