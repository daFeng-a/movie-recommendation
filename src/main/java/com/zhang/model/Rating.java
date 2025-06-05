package com.zhang.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评分实体类，对应数据库中的 ratings 表
 */
@Data
public class Rating {
    /**
     * 评分ID
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
     * 评分（范围：0.5 - 5.0）
     */
    private Double rating;

    /**
     * 评分时间
     */
    private LocalDateTime timestamp;
}
