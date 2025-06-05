package com.zhang.controller;

import com.zhang.entity.Rating;
import com.zhang.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 评分相关接口
 */
@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    /**
     * 新增评分接口，用户打分后自动刷新其推荐
     */
    @PostMapping
    public String addRating(@RequestBody Rating rating) {
        ratingService.addRating(rating);
        return "success";
    }
}