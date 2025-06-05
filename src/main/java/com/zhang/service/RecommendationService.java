package com.zhang.service;

import es.upm.etsisi.cf4j.recommender.knn.UserKNN;
import es.upm.etsisi.cf4j.recommender.knn.similarities.Corr;
import es.upm.etsisi.cf4j.util.Dataset;
import es.upm.etsisi.cf4j.data.DataSet;
import com.zhang.mapper.RatingMapper;
import com.zhang.mapper.RecommendationMapper;
import com.zhang.model.Rating;
import com.zhang.model.Recommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 推荐服务类，封装 CF4J 推荐算法逻辑。
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RatingMapper ratingMapper;
    private final RecommendationMapper recommendationMapper;

    /**
     * 构建 CF4J 数据集
     */
    private DataSet buildDataSet() {
        List<Rating> ratings = ratingMapper.selectList(null);
        DataSet dataset = new Dataset();

        for (Rating rating : ratings) {
            String userCode = rating.getUserId().toString();
            String itemCode = rating.getMovieId().toString();
            double ratingValue = rating.getRating();

            dataset.addRating(userCode, itemCode, ratingValue);
        }

        return dataset;
    }

    /**
     * 为指定用户生成个性化推荐（实时触发）
     *
     * @param userId 用户ID
     */
    public void generateRecommendationsForUser(Long userId) {
        DataSet dataset = buildDataSet();
        UserKNN recommender = new UserKNN(dataset);
        recommender.setSimilarityMetric(new Corr());
        recommender.setNumberOfNeighbors(10);
        recommender.evaluate();

        String userCode = userId.toString();
        List<String> recommendedItems = recommender.getRecommendations(userCode, 10);

        // 清除旧推荐
        recommendationMapper.deleteByUserId(userId);

        // 存储新推荐
        for (String movieIdStr : recommendedItems) {
            Long movieId = Long.valueOf(movieIdStr);
            Double predictedRating = recommender.predict(userCode, movieIdStr);

            Recommendation recommendation = new Recommendation();
            recommendation.setUserId(userId);
            recommendation.setMovieId(movieId);
            recommendation.setPredictedRating(predictedRating);
            recommendationMapper.insert(recommendation);
        }
    }

    /**
     * 全量生成所有用户的推荐（用于定时任务）
     */
    public void generateAllRecommendations() {
        DataSet dataset = buildDataSet();
        UserKNN recommender = new UserKNN(dataset);
        recommender.setSimilarityMetric(new Corr());
        recommender.setNumberOfNeighbors(10);
        recommender.evaluate();

        List<String> allUsers = dataset.getUsers();
        recommendationMapper.truncateTable(); // 清空表

        for (String userCode : allUsers) {
            Long userId = Long.valueOf(userCode);
            List<String> recommendedItems = recommender.getRecommendations(userCode, 10);

            for (String movieIdStr : recommendedItems) {
                Long movieId = Long.valueOf(movieIdStr);
                Double predictedRating = recommender.predict(userCode, movieIdStr);

                Recommendation recommendation = new Recommendation();
                recommendation.setUserId(userId);
                recommendation.setMovieId(movieId);
                recommendation.setPredictedRating(predictedRating);
                recommendationMapper.insert(recommendation);
            }
        }
    }
}
