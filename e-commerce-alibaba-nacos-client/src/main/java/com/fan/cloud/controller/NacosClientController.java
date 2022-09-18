package com.fan.cloud.controller;

import com.fan.cloud.service.NacosClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <h1>nacos client controller</h1>
 */
@Slf4j
@RestController
@RequestMapping("/nacos-client")
public class NacosClientController {

    @Resource
    private NacosClientService nacosClientService;

    /**
     * <h2>根据service id获取服务id下的所有实例信息</h2>
     */
    @GetMapping("/service-instance")
    public List<ServiceInstance> logNacosClientInfo(@RequestParam(defaultValue = "e-commerce-nacos-client") String serviceId) {
        log.info("coming in log nacos client info: [{}]", serviceId);
        //throw new RuntimeException("运行时异常！");
        return nacosClientService.getNacosClientInfo(serviceId);
    }
}
