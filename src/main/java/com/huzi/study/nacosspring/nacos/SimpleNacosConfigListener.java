package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Properties;


/**
 * @author lsx
 * @date 2020/7/31 7:48
 */
@Configuration
public class SimpleNacosConfigListener {

    public static final String PROPERTIES_DATA_ID = "properties-data-id";

    private static final Logger logger = LoggerFactory.getLogger(SimpleNacosConfigListener.class);

    @NacosInjected
    private ConfigService configService;

    @PostConstruct
    public void init() throws NacosException {
        StringBuilder builder = new StringBuilder();
        builder.append("user.id = 1");
        builder.append(SystemUtils.LINE_SEPARATOR);
        builder.append("user.name = mercyblitz");
        builder.append(SystemUtils.LINE_SEPARATOR);
        builder.append("user.github = https://github.com/mercyblitz");
        configService.publishConfig(PROPERTIES_DATA_ID, "DEFAULT_GROUP", builder.toString());
    }

    @NacosConfigListener(dataId = PROPERTIES_DATA_ID)
    public void onReceived(String value) {
        logger.info("onReceived(String)：{}", value);
    }

    @NacosConfigListener(dataId = PROPERTIES_DATA_ID)
    public void onReceived(Properties value) {
        logger.info("onReceived(Properties)：{}", value);
    }
}
