package com.zhang.service.impl;

import com.zhang.mapper.UserActionMapper;
import com.zhang.service.UserActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户行为服务实现
 */
@Service
@RequiredArgsConstructor
public class UserActionServiceImpl implements UserActionService {
    private final UserActionMapper userActionMapper;

    @Override
    public void saveAction(Integer userId, Integer movieId, String actionType) {
        userActionMapper.insert(userId, movieId, actionType);
    }
}