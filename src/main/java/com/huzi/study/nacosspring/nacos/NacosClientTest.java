package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;

/**
 * @author lsx
 * @date 2020/8/7 16:49
 */
public class NacosClientTest {

    public static void main(String[] args) {
        try {
            String serverAddress = "127.0.0.1:8848";
            String dataId = "test";
            String group = "DEFAULT_GROUP";
            Properties properties = new Properties();
            properties.setProperty("serverAddr", serverAddress);
            properties.setProperty("username", "nacos");
            properties.setProperty("password", "nacos");
            ConfigService configService = NacosFactory.createConfigService(properties);
            configService.removeConfig(dataId, group);
            //System.out.println(config);
            //String content = configService.getConfig(dataId, group, 5000);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
