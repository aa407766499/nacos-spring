package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;

/**
 * @author lsx
 * @date 2020/8/16 20:55
 */
public class NacosNamingTest {

    public static void main(String[] args) {
        NamingService naming = null;
        try {
            naming = NamingFactory.createNamingService("127.0.0.1:8848");
            naming.registerInstance("nacos.test.3", "11.11.11.11", 8888, "TEST1");
        } catch (NacosException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
