package com.zhang.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类，对应users表
 */
@Data
public class User {
    private Integer id;              // 用户ID
    private String username;         // 用户名
    private String password;         // 加密密码
    private String email;            // 邮箱
    private LocalDateTime createdAt; // 注册时间
}