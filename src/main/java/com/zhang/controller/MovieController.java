package com.zhang.controller;

import com.zhang.entity.Movie;
import com.zhang.entity.Rating;
import com.zhang.entity.Recommendation;
import com.zhang.entity.User;
import com.zhang.mapper.MovieMapper;
import com.zhang.mapper.RecommendationMapper;
import com.zhang.service.RatingService;
import com.zhang.service.UserActionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电影与推荐页面控制器
 */
@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieMapper movieMapper;
    private final RecommendationMapper recommendationMapper;
    private final RatingService ratingService;
    private final UserActionService userActionService;

    /**
     * 电影列表页面
     */
    @GetMapping("/movies")
    public String allMovies(Model model) {
        List<Movie> movies = movieMapper.selectAll();
        model.addAttribute("movies", movies);
        return "movies";
    }

    /**
     * 电影详情页面，支持评分、收藏、观看等行为
     */
    @GetMapping("/movies/{id}")
    public String movieDetail(@PathVariable Integer id, Model model, HttpSession session) {
        Movie movie = movieMapper.selectById(id);
        model.addAttribute("movie", movie);
        // 埋点：浏览行为
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userActionService.saveAction(user.getId(), id, "view");
        }
        return "movie_detail";
    }

    /**
     * 评分提交
     */
    @PostMapping("/rate")
    public String rateMovie(@RequestParam Integer movieId, @RequestParam Double rating, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            ratingService.addRating(new Rating(null, user.getId(), movieId, rating, null));
        }
        return "redirect:/movies/" + movieId;
    }

    /**
     * 收藏/观看等行为
     */
    @PostMapping("/action")
    public String userAction(@RequestParam Integer movieId, @RequestParam String actionType, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userActionService.saveAction(user.getId(), movieId, actionType);
        }
        return "redirect:/movies/" + movieId;
    }

    /**
     * 推荐列表页面
     */
    @GetMapping("/recommend")
    public String recommendList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Recommendation> recs = (user == null) ? List.of() : recommendationMapper.selectByUserId(user.getId());
        recs.forEach(rec -> rec.setMovie(movieMapper.selectById(rec.getMovieId())));
        model.addAttribute("recommendList", recs);
        return "recommend";
    }
}