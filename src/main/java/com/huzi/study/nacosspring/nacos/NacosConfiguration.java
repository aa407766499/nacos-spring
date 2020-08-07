package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import com.huzi.study.nacosspring.test.ConfigurationPropertiesTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lsx
 * @date 2020/7/29 21:17
 */
@Configuration
@EnableNacos(globalProperties = @NacosProperties(serverAddr = "${nacos.server-addr}",enableRemoteSyncConfig = "true",maxRetry = "5",configRetryTime = "4000",configLongPollTimeout = "26000",username = "nacos",password = "nacos"))
public class NacosConfiguration {

    public static final String CURRENT_TIME_DATA_ID = "time-data-id";

    @NacosInjected
    private NamingService namingService;

    @NacosInjected(properties = @NacosProperties(encode = "UTF-8"))
    private NamingService namingServiceUTF8;

    @NacosInjected
    private ConfigService configService;

    @Autowired
    private ConfigurationPropertiesTest propertiesTest;

    @Autowired
    private Apple apple;

    @PostConstruct
    public void init() {
        if (namingService != namingServiceUTF8) {
            throw new RuntimeException("why?");
        }
        System.out.println(apple);
    }

}
