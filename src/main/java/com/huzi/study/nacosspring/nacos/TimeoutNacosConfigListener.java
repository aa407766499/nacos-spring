package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lsx
 * @date 2020/8/5 9:00
 */
@Configuration
@Slf4j
public class TimeoutNacosConfigListener {

    public static final String TEST_DATA_ID = "timeout-data-id";

    @NacosInjected
    private ConfigService configService;

    @PostConstruct
    public void init() throws NacosException {
        configService.publishConfig(TEST_DATA_ID, "DEFAULT_GROUP", "Hello,World");
    }

    @NacosConfigListener(dataId = TEST_DATA_ID, timeout = 100)
    public void onReceivedWithTimeout(String value) throws InterruptedException {
        Thread.sleep(200);
        log.info("onReceivedWithTimeout(String):{}", value);
    }

    @NacosConfigListener(dataId = TEST_DATA_ID, timeout = 100)
    public void onReceivedWithoutTimeout(String value) throws InterruptedException {
        Thread.sleep(50);
        log.info("onReceivedWithTimeout(String):{}", value);
    }
}
