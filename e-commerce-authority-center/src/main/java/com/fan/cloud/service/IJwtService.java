package com.fan.cloud.service;

import com.fan.cloud.vo.UsernameAndPassword;

/**
 * JWT 相关服务接口定义
 */
public interface IJwtService {

    /**
     * 生成 JWT Token，使用默认的超时时间
     */
    String generateToken(String username, String password) throws Exception;

    /**
     * 生成指定超时时间的 Token，单位是天
     */
    String generateToken(String username, String password, int expire) throws Exception;

    /**
     * 注册用户并生成 Token 返回
     */
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;
}
