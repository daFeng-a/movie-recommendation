package com.zhang.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类，对应数据库中的 users 表
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名（唯一）
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
