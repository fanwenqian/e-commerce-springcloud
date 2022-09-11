package com.fan.cloud.advice;

import com.fan.cloud.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>全局异常捕获处理</h1>
 *
 * @author fan.wenqian
 * @date 2022-09-11
 */
@Slf4j
@RestControllerAdvice("com.fan.cloud")
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerGlobalException(HttpServletRequest request, Exception exception) {
        CommonResponse<String> response = new CommonResponse<>(-1, "异常响应！");
        response.setData(exception.getMessage());
        log.error("service has error : [{}]", exception.getMessage(), exception);
        return response;
    }
}
