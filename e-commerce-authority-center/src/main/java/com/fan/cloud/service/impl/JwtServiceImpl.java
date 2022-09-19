package com.fan.cloud.service.impl;

import cn.hutool.crypto.asymmetric.Sign;
import com.alibaba.fastjson.JSON;
import com.fan.cloud.constant.AuthorityConstant;
import com.fan.cloud.constant.CommonConstant;
import com.fan.cloud.dao.EcommerceUserDao;
import com.fan.cloud.entity.EcommerceUser;
import com.fan.cloud.service.IJwtService;
import com.fan.cloud.vo.LoginUserInfo;
import com.fan.cloud.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * jwt 相关服务接口实现
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JwtServiceImpl implements IJwtService {

    @Resource
    private EcommerceUserDao ecommerceUserDao;

    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(String username, String password, int expire) throws Exception {
        //首先需要验证用户是否能否通过授权验证，即输入的用户名和密码能否匹配数据表记录
        EcommerceUser ecommerceUser = ecommerceUserDao.findByUsernameAndPassword(username, password);
        if (null == ecommerceUser) {
            log.error("can not find user: [{}],[{}]", username, password);
            return null;
        }

        // Token 中塞入对象，即 JWT 中存储的信息，后端拿到这些信息就可以知道是哪个用户在操作
        LoginUserInfo loginUserInfo = new LoginUserInfo(ecommerceUser.getId(), ecommerceUser.getUsername());

        // 如果设置的超时时间小于等于0，则设置超时时间为默认值
        if (expire <= 0) {
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }

        //计算超时时间
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                // jwt Payload --> KV
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                // jwt id 无意义
                .setId(UUID.randomUUID().toString())
                // jwt 超时时间
                .setExpiration(expireDate)
                // jwt 签名 --> 指定了一个加密算法
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {
        // 先去校验用户名是否存在，如果存在，不能重复注册
        EcommerceUser oldUser =
                ecommerceUserDao.findByUsername(usernameAndPassword.getUsername());
        if (null != oldUser) {
            log.error("username is registered: [{}]" + oldUser.getUsername());
            return null;
        }

        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername(usernameAndPassword.getUsername());
        ecommerceUser.setPassword(usernameAndPassword.getPassword());

        //注册一个新用户
        ecommerceUserDao.insert(ecommerceUser);
        log.info("rigister user success: [{}],[{}]", ecommerceUser.getUsername(), ecommerceUser.getId());

        //生成 token 并返回
        return generateToken(ecommerceUser.getUsername(), ecommerceUser.getPassword());
    }

    /**
     * 根据本地存储的私钥获取到 PrivateKey 对象
     */
    private PrivateKey getPrivateKey() throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
