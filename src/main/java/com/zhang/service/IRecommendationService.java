package com.zhang.service;

public interface IRecommendationService {

    /**
     * 为指定用户生成个性化推荐（实时触发）
     *
     * @param userId 用户ID
     */
    void generateRecommendationsForUser(Long userId);

    /**
     * 全量生成所有用户的推荐（用于定时任务）
     */
    void generateAllRecommendations();
}
