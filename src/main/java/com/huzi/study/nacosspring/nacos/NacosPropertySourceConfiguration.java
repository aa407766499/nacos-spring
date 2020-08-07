package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Properties;

import static com.huzi.study.nacosspring.nacos.NacosPropertySourceConfiguration.*;
import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;
import static org.springframework.core.env.StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME;

/**
 * @author lsx
 * @date 2020/8/5 13:52
 */
@Configuration
@Slf4j
@NacosPropertySource(name = "first", dataId = FIRST_DATA_ID, first = true, autoRefreshed = true)
@NacosPropertySources({
        @NacosPropertySource(name = "before-os-env", dataId = BEFORE_OS_ENV_DATA_ID, before = SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME),
        @NacosPropertySource(name = "after-system-properties", dataId = AFTER_SYS_PROP_DATA_ID, after = SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME),
        @NacosPropertySource(dataId = "people", groupId = "DEVELOP")
})
public class NacosPropertySourceConfiguration {

    public static final String FIRST_DATA_ID = "first-property-source-data-id";

    public static final String BEFORE_OS_ENV_DATA_ID = "before-os-env-property-source-data-id";

    public static final String AFTER_SYS_PROP_DATA_ID = "after-system-properties-property-source-data-id";

    static {
        Properties props = new Properties();
        props.put(PropertyKeyConst.SERVER_ADDR, System.getProperty("nacos.server-addr", "192.168.1.113:8848"));
        props.put(PropertyKeyConst.NAMESPACE, System.getProperty("nacos.config.namespace", ""));
        props.put(PropertyKeyConst.USERNAME, System.getProperty("nacos.username", "nacos"));
        props.put(PropertyKeyConst.PASSWORD, System.getProperty("nacos.password", "nacos"));
        try {
            ConfigService configService = NacosFactory.createConfigService(props);
            publishConfig(configService, FIRST_DATA_ID, "user.name = huzi");
            publishConfig(configService, BEFORE_OS_ENV_DATA_ID, "PATH = /home/my-path");
            publishConfig(configService, AFTER_SYS_PROP_DATA_ID, "user.name = liu");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Value("${PATH}")
    private String path;

    @Value("${user.name}")
    private String username;

    private static void publishConfig(ConfigService configService, String dataId, String propertiesContent) throws NacosException {
        configService.publishConfig(dataId, "DEFAULT_GROUP", propertiesContent);
    }

    @PostConstruct
    public void init() {
        log.info("path:{}", path);
        log.info("user.name:{}", username);
        log.info("Java System user.name:{}", System.getProperty("user.name"));
        log.info("OS Env path:{}", System.getenv("PATH"));
    }

}
