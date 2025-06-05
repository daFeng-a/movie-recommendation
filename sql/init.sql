-- 创建数据库 movie_recommend
CREATE DATABASE IF NOT EXISTS movie_recommend;
USE movie_recommend;

-- 用户表：存储用户基本信息
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
                       username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名（唯一）',
                       password VARCHAR(100) NOT NULL COMMENT '密码',
                       email VARCHAR(100) NOT NULL COMMENT '邮箱',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

-- 电影表：存储电影相关信息
CREATE TABLE movies (
                        id INT AUTO_INCREMENT PRIMARY KEY COMMENT '电影ID',
                        title VARCHAR(200) NOT NULL COMMENT '电影名称',
                        year INT COMMENT '上映年份',
                        genres VARCHAR(100) COMMENT '电影类型（多个类型可用逗号分隔）',
                        director VARCHAR(100) COMMENT '导演',
                        actors VARCHAR(300) COMMENT '演员列表',
                        plot TEXT COMMENT '剧情简介',
                        poster_url VARCHAR(300) COMMENT '海报图片链接'
);

-- 评分表：记录用户对电影的评分
CREATE TABLE ratings (
                         id INT AUTO_INCREMENT PRIMARY KEY COMMENT '评分ID',
                         user_id INT NOT NULL COMMENT '关联的用户ID',
                         movie_id INT NOT NULL COMMENT '关联的电影ID',
                         rating DOUBLE NOT NULL CHECK (rating >= 0.5 AND rating <= 5.0) COMMENT '评分（范围：0.5 - 5.0）',
                         timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '评分时间',
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (movie_id) REFERENCES movies(id)
);

-- 推荐结果表：存储系统生成的推荐结果
CREATE TABLE recommendations (
                                 id INT AUTO_INCREMENT PRIMARY KEY COMMENT '推荐ID',
                                 user_id INT NOT NULL COMMENT '关联的用户ID',
                                 movie_id INT NOT NULL COMMENT '关联的电影ID',
                                 predicted_rating DOUBLE NOT NULL COMMENT '预测评分',
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '推荐生成时间',
                                 FOREIGN KEY (user_id) REFERENCES users(id),
                                 FOREIGN KEY (movie_id) REFERENCES movies(id)
);

-- 为评分表添加复合索引以提高查询效率
CREATE INDEX idx_user_movie ON ratings(user_id, movie_id);
