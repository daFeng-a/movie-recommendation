package com.zhang.service;

/**
 * 用户行为服务接口
 */
public interface UserActionService {
    /**
     * 记录用户行为
     */
    void saveAction(Integer userId, Integer movieId, String actionType);
}