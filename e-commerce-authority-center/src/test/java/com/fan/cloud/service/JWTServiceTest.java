package com.fan.cloud.service;

import com.alibaba.fastjson.JSON;
import com.fan.cloud.util.TokenParseUtil;
import com.fan.cloud.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JWTServiceTest {

    @Resource
    private IJwtService jwtService;

    @Test
    public void testGenerateAndParseToken() throws Exception {
        String token = jwtService.generateToken("fan.wenqian", "e10adc3949ba59abbe56e057f20f883e");
        log.info("jwt token is : [{}]", token);

        LoginUserInfo userInfo = TokenParseUtil.parseUserInfoFromToken(token);
        log.info("parse token: [{}]", JSON.toJSONString(userInfo));
    }
}
