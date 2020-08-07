package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.event.config.NacosConfigListenerRegisteredEvent;
import com.alibaba.nacos.spring.context.event.config.NacosConfigReceivedEvent;
import com.alibaba.nacos.spring.context.event.config.NacosConfigRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lsx
 * @date 2020/8/5 15:33
 */
@Configuration
@Slf4j
public class NacosEventListenerConfiguration {

    private static final String DATA_ID = "event-data-id";

    @NacosInjected
    private ConfigService configService;

    @PostConstruct
    public void init() throws NacosException {
        configService.publishConfig(DATA_ID, "DEFAULT_GROUP", "hello,world");
        configService.removeConfig(DATA_ID, "DEFAULT_GROUP");

        Listener listener = new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
            }
        };

        configService.addListener(DATA_ID, "DEFAULT_GROUP", listener);
        configService.removeListener(DATA_ID, "DEFAULT_GROUP", listener);
    }

    @Bean
    public ApplicationListener<NacosConfigReceivedEvent> nacosConfigReceivedEventListener() {
        return new ApplicationListener<NacosConfigReceivedEvent>() {
            @Override
            public void onApplicationEvent(NacosConfigReceivedEvent event) {
                log.info("listener on nacosConfigReceivedEvent - dataId : {},groupId : {},content: {},source:{}",
                        event.getDataId(), event.getGroupId(), event.getContent(), event.getSource());
            }
        };
    }

    @Bean
    public ApplicationListener<NacosConfigRemovedEvent> nacosConfigRemovedEventListener() {
        return new ApplicationListener<NacosConfigRemovedEvent>() {
            @Override
            public void onApplicationEvent(NacosConfigRemovedEvent event) {
                log.info("listener on nacosConfigReceivedEvent - dataId : {},groupId : {},content: {},source:{}", event.getDataId(), event.getGroupId(), event.isRemoved(), event.getSource());
            }
        };
    }

    @Bean
    public ApplicationListener<NacosConfigListenerRegisteredEvent> nacosConfigListenerRegisteredEventListener() {
        return new ApplicationListener<NacosConfigListenerRegisteredEvent>() {
            @Override
            public void onApplicationEvent(NacosConfigListenerRegisteredEvent event) {
                log.info("listener on nacosConfigReceivedEvent - dataId : {},groupId : {},content: {},source:{}", event.getDataId(), event.getGroupId(), event.isRegistered(), event.getSource());
            }
        };
    }
}


