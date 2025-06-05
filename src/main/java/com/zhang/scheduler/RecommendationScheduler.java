package com.zhang.scheduler;

import com.zhang.service.CollaborativeFilteringService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 推荐列表定时任务（每日凌晨刷新所有用户推荐）
 */
@Component
@RequiredArgsConstructor
public class RecommendationScheduler {
    private final CollaborativeFilteringService collaborativeFilteringService;

    /**
     * 每天凌晨1点批量刷新所有用户的推荐列表
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateAllRecommendations() {
        collaborativeFilteringService.generateRecommendationsForAllUsers();
    }
}