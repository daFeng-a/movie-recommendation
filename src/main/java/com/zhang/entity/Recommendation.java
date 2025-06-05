package com.zhang.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 推荐结果实体类，对应recommendations表
 */
@Data
public class Recommendation {
    private Integer id;              // 推荐ID
    private Integer userId;          // 用户ID
    private Integer movieId;         // 电影ID
    private Double predictedRating;  // 推荐预测评分
    private LocalDateTime createdAt; // 推荐生成时间
    // 关联Movie便于前端展示
    private Movie movie;             // 推荐电影详情
}