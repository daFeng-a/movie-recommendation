package com.zhang.entity;

import lombok.Data;

/**
 * 电影实体类，对应movies表
 */
@Data
public class Movie {
    private Integer id;         // 电影ID
    private String title;       // 电影名称
    private Integer year;       // 上映年份
    private String genres;      // 电影类型
    private String director;    // 导演
    private String actors;      // 演员
    private String plot;        // 剧情简介
    private String posterUrl;   // 海报图片链接
    private String videoUrl;    // 电影播放路径
}