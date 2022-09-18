package com.fan.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPassword {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;
}
