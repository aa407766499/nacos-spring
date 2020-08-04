package com.huzi.study.nacosspring.test;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lsx
 * @date 2020/7/31 21:25
 */
@ConfigurationProperties(prefix = "huzi")
public class ConfigurationPropertiesTest {

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
