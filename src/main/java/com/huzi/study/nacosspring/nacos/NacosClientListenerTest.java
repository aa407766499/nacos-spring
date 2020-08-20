package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author lsx
 * @date 2020/8/12 14:29
 */
public class NacosClientListenerTest {

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
            String content = configService.getConfig(dataId, group, 5000);
            System.out.println(content);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("recieve1:" + configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
// 测试让主线程不退出，因为订阅配置是守护线程，主线程退出守护线程就会退出。 正式代码中无需下面代码
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
