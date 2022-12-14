package com.fan.cloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class NacosClientService {

    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * <h2>打印 Nacos Client 信息到日志中</h2>
     *
     * @param serviceId 应用名称 ${spring.application.name}
     * @return 服务实例们
     */
    public List<ServiceInstance> getNacosClientInfo(String serviceId) {
        log.info("request nacos client to get service instance info: [{}]", serviceId);
        return discoveryClient.getInstances(serviceId);
    }
}
