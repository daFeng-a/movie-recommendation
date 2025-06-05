package com.zhang.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 推荐结果实体类，对应数据库中的 recommendations 表
 */
@Data
public class Recommendation {
    /**
     * 推荐ID
     */
    private Long id;

    /**
     * 关联的用户ID
     */
    private Long userId;

    /**
     * 关联的电影ID
     */
    private Long movieId;

    /**
     * 预测评分
     */
    private Double predictedRating;

    /**
     * 推荐生成时间
     */
    private LocalDateTime createdAt;
}
