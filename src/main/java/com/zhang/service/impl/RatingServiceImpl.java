package com.zhang.service.impl;

import com.zhang.entity.Rating;
import com.zhang.mapper.RatingMapper;
import com.zhang.service.CollaborativeFilteringService;
import com.zhang.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评分服务实现
 */
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingMapper ratingMapper;
    private final CollaborativeFilteringService collaborativeFilteringService;

    @Override
    @Transactional
    public void addRating(Rating rating) {
        ratingMapper.insert(rating);
        // 新评分后刷新该用户的推荐
        collaborativeFilteringService.generateRecommendationsForUser(rating.getUserId());
    }
}