package com.zhang.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户行为实体类，对应user_actions表
 */
@Data
public class UserAction {
    private Integer id;            // 行为ID
    private Integer userId;        // 用户ID
    private Integer movieId;       // 电影ID
    private String actionType;     // 行为类型(view/watch/favorite/like)
    private LocalDateTime actionTime; // 行为时间
}