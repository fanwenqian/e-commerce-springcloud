package com.fan.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目通用返回类型
 *
 * @param <T>
 * @author fan.wenqian
 * @date 2022-09-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> implements Serializable {

    /**
     * 请求状态码
     */
    private Integer code;

    /**
     * 请求信息
     */
    private String message;

    /**
     * 请求返回实体
     */
    private T data;

    public CommonResponse(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
