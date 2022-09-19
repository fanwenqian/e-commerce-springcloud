package com.fan.cloud.controller;

import com.fan.cloud.annotation.IgnoreResponseAdvice;
import com.fan.cloud.service.IJwtService;
import com.fan.cloud.vo.JwtToken;
import com.fan.cloud.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Resource
    private IJwtService jwtService;

    /**
     * 从授权中心获取 token (其实就是登录功能)，且返回信息中没有统一响应的包装
     */
    @IgnoreResponseAdvice
    @PostMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("request to get token with param: [{}]", usernameAndPassword);
        return new JwtToken(
                jwtService.generateToken(usernameAndPassword.getUsername(),
                        usernameAndPassword.getPassword())
        );
    }

    @IgnoreResponseAdvice
    @PostMapping("/register")
    public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("request to get token with param: [{}]", usernameAndPassword);
        return new JwtToken(
                jwtService.registerUserAndGenerateToken(usernameAndPassword)
        );
    }
}
