package com.fan.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo {

    /** 用户id */
    private Long id;

    /** 用户名 */
    private String username;
}
