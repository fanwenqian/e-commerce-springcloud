package com.fan.cloud.advice;

import com.fan.cloud.annotation.IgnoreResponseAdvice;
import com.fan.cloud.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <h1>实现统一响应</h1>
 *
 * @author fan.wenqian
 * @date 2022-09-11
 */
@RestControllerAdvice("com.fan.cloud")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * <h2>判断是否需要对响应进行处理</h2>
     *
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class aClass) {
        //当前方法的类标识了@IgnoreResponseAdvice注解，则不处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        //当前方法标识了@IgnoreResponseAdvice注解，则不处理
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        //定义最终的返回对象
        CommonResponse<Object> response = new CommonResponse<>(00000,"正常响应！");
        if (null == o) {
            return response;
        } else if (o instanceof CommonResponse) {
            response = (CommonResponse<Object>) o;
        } else {
            response.setData(o);
        }
        return response;
    }
}
