package com.zhang.service.impl;

import com.zhang.entity.Movie;
import com.zhang.entity.Rating;
import com.zhang.entity.Recommendation;
import com.zhang.mapper.MovieMapper;
import com.zhang.mapper.RatingMapper;
import com.zhang.mapper.RecommendationMapper;
import com.zhang.service.CollaborativeFilteringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 协同过滤推荐服务实现
 */
@Service
@RequiredArgsConstructor
public class CollaborativeFilteringServiceImpl implements CollaborativeFilteringService {
    private final RatingMapper ratingMapper;
    private final RecommendationMapper recommendationMapper;
    private final MovieMapper movieMapper;

    /**
     * 为指定用户生成协同过滤推荐（皮尔逊相似度）
     */
    @Override
    @Transactional
    public List<Recommendation> generateRecommendationsForUser(Integer userId) {
        // 获取所有评分
        List<Rating> allRatings = ratingMapper.selectAll();
        // 构建用户-电影-评分Map
        Map<Integer, Map<Integer, Double>> userMovieRatings = new HashMap<>();
        for (Rating rating : allRatings) {
            userMovieRatings.computeIfAbsent(rating.getUserId(), k -> new HashMap<>())
                .put(rating.getMovieId(), rating.getRating());
        }

        // 当前用户评分
        Map<Integer, Double> targetRatings = userMovieRatings.getOrDefault(userId, new HashMap<>());
        Set<Integer> allMovieIds = allRatings.stream().map(Rating::getMovieId).collect(Collectors.toSet());

        // 计算用户相似度
        Map<Integer, Double> userSimilarity = new HashMap<>();
        for (Integer otherUserId : userMovieRatings.keySet()) {
            if (otherUserId.equals(userId)) continue;
            double sim = pearsonSimilarity(targetRatings, userMovieRatings.get(otherUserId));
            if (sim > 0) userSimilarity.put(otherUserId, sim);
        }

        // 预测用户未评分电影的兴趣度
        Map<Integer, Double> predictedRatings = new HashMap<>();
        for (Integer movieId : allMovieIds) {
            if (targetRatings.containsKey(movieId)) continue; // 跳过已评分
            double numerator = 0, denominator = 0;
            for (Map.Entry<Integer, Double> entry : userSimilarity.entrySet()) {
                Integer otherUserId = entry.getKey();
                Double similarity = entry.getValue();
                Double otherRating = userMovieRatings.get(otherUserId).get(movieId);
                if (otherRating != null) {
                    numerator += similarity * otherRating;
                    denominator += similarity;
                }
            }
            if (denominator > 0) predictedRatings.put(movieId, numerator / denominator);
        }

        // 选取top20推荐
        List<Recommendation> recommendations = predictedRatings.entrySet().stream()
            .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
            .limit(20)
            .map(e -> {
                Recommendation r = new Recommendation();
                r.setUserId(userId);
                r.setMovieId(e.getKey());
                r.setPredictedRating(e.getValue());
                r.setMovie(movieMapper.selectById(e.getKey()));
                return r;
            }).collect(Collectors.toList());

        // 清理并插入新推荐
        recommendationMapper.deleteByUserId(userId);
        for (Recommendation r : recommendations) {
            recommendationMapper.insert(r);
        }
        return recommendations;
    }

    /**
     * 批量为所有用户生成推荐
     */
    @Override
    @Transactional
    public void generateRecommendationsForAllUsers() {
        List<Rating> allRatings = ratingMapper.selectAll();
        List<Integer> allUserIds = allRatings.stream().map(Rating::getUserId).distinct().collect(Collectors.toList());
        for (Integer userId : allUserIds) {
            generateRecommendationsForUser(userId);
        }
    }

    /**
     * 皮尔逊相关系数，相似度计算
     */
    private double pearsonSimilarity(Map<Integer, Double> a, Map<Integer, Double> b) {
        Set<Integer> common = new HashSet<>(a.keySet());
        common.retainAll(b.keySet());
        int n = common.size();
        if (n < 2) return 0;
        double sumA = 0, sumB = 0, sumA2 = 0, sumB2 = 0, sumAB = 0;
        for (Integer movieId : common) {
            double ra = a.get(movieId);
            double rb = b.get(movieId);
            sumA += ra;
            sumB += rb;
            sumA2 += ra * ra;
            sumB2 += rb * rb;
            sumAB += ra * rb;
        }
        double numerator = sumAB - (sumA * sumB / n);
        double denominator = Math.sqrt((sumA2 - sumA * sumA / n) * (sumB2 - sumB * sumB / n));
        if (denominator == 0) return 0;
        return numerator / denominator;
    }
}