package com.zhang.model;

import lombok.Data;

/**
 * 电影实体类，对应数据库中的 movies 表
 */
@Data
public class Movie {
    /**
     * 电影ID
     */
    private Long id;

    /**
     * 电影名称
     */
    private String title;

    /**
     * 上映年份
     */
    private Integer year;

    /**
     * 电影类型（多个类型可用逗号分隔）
     */
    private String genres;

    /**
     * 导演
     */
    private String director;

    /**
     * 演员列表
     */
    private String actors;

    /**
     * 剧情简介
     */
    private String plot;

    /**
     * 海报图片链接
     */
    private String posterUrl;
}
