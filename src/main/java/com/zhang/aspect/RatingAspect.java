package com.zhang.aspect;

import com.zhang.model.Rating;
import com.zhang.service.IRecommendationService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class RatingAspect {

    private final IRecommendationService recommendationService;

    public RatingAspect(IRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @After("execution(* com.zhang.mapper.RatingMapper.insert(..)) && args(rating)")
    public void afterInsertRating(Rating rating) {
        updateRecommendations(rating.getUserId());
    }

    @After("execution(* com.zhang.mapper.RatingMapper.updateById(..)) && args(rating)")
    public void afterUpdateRating(Rating rating) {
        updateRecommendations(rating.getUserId());
    }

    private void updateRecommendations(Long userId) {
        if (Objects.nonNull(userId)) {
            recommendationService.generateRecommendationsForUser(userId);
        }
    }
}