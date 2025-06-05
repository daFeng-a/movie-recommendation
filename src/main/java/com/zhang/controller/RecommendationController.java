package com.zhang.controller;

import com.zhang.entity.Recommendation;
import com.zhang.mapper.RecommendationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推荐结果查询接口
 */
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationMapper recommendationMapper;

    /**
     * 获取指定用户的推荐列表
     */
    @GetMapping("/{userId}")
    public List<Recommendation> getRecommendations(@PathVariable Integer userId) {
        return recommendationMapper.selectByUserId(userId);
    }
}