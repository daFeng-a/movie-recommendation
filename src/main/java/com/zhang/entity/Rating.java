package com.zhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评分实体类，对应ratings表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Integer id;              // 评分ID
    private Integer userId;          // 用户ID
    private Integer movieId;         // 电影ID
    private Double rating;           // 用户评分
    private LocalDateTime timestamp; // 评分时间
}