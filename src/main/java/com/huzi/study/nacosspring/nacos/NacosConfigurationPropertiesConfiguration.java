package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.huzi.study.nacosspring.nacos.Pojo.DATA_ID;

/**
 * @author lsx
 * @date 2020/8/5 10:51
 */
@Slf4j
@Configuration
public class NacosConfigurationPropertiesConfiguration {

    @NacosInjected
    private ConfigService configService;

    @Autowired
    private Pojo pojo;

    @Bean
    public Pojo pojo() {
        return new Pojo();
    }

    @PostConstruct
    public void init() throws NacosException {
        log.info("pojo = {}", pojo);
        configService.publishConfig(DATA_ID, "DEFAULT_GROUP", "pojo.id = 1");
        configService.publishConfig(DATA_ID, "DEFAULT_GROUP", "pojo.name = huzi");
        configService.publishConfig(DATA_ID, "DEFAULT_GROUP", "pojo.desc = haha");
        configService.publishConfig(DATA_ID, "DEFAULT_GROUP", "pojo.ignored = true");
        log.info("pojo.id = {}", pojo.getId());
        log.info("pojo.name = {}", pojo.getName());
        log.info("pojo.description = {}", pojo.getDescription());
        log.info("pojo.ignored = {}", pojo.isIgnored());
    }

}
