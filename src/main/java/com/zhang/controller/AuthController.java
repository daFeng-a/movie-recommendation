package com.zhang.controller;

import com.zhang.entity.User;
import com.zhang.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 认证相关控制器，处理登录、注册、登出
 */
@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 登录页
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 注册页
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        if (userMapper.selectByUsername(username) != null) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        // 加密存储
        String encoded = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoded);
        user.setEmail(email);
        userMapper.insert(user);
        return "redirect:/login";
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}