package com.fan.cloud.service;

import cn.hutool.crypto.digest.MD5;
import com.fan.cloud.dao.EcommerceUserDao;
import com.fan.cloud.entity.EcommerceUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * EcommerceUser 相关的测试
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EcommerceUserTest {

    @Resource
    private EcommerceUserDao ecommerceUserDao;

    @Test
    public void createUserRecord() {
        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername("fan.wenqian2");
        ecommerceUser.setPassword(MD5.create().digestHex("123456"));
        ecommerceUser.setExtraInfo("{}");
        ecommerceUserDao.insert(ecommerceUser);
    }

    @Test
    public void updateUserRecord() {
        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setId(12L);
        ecommerceUser.setPassword(MD5.create().digestHex("1234567"));
        ecommerceUser.setExtraInfo("{}");
        ecommerceUserDao.updateById(ecommerceUser);
    }
}
