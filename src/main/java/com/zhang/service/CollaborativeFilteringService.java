package com.zhang.service;

import com.zhang.entity.Recommendation;
import java.util.List;

/**
 * 协同过滤推荐服务接口
 */
public interface CollaborativeFilteringService {
    List<Recommendation> generateRecommendationsForUser(Integer userId);
    void generateRecommendationsForAllUsers();
}