package com.fan.cloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fan.cloud.entity.EcommerceUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EcommerceUserDao extends BaseMapper<EcommerceUser> {

    /**
     * 根据用户名查询 EcommerceUser 对象
     */
    EcommerceUser findByUsername(String username);

    /**
     * 根据用户名和密码查询 EcommerceUser 对象
     */
    EcommerceUser findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
