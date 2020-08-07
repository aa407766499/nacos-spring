package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.api.config.annotation.NacosIgnore;
import com.alibaba.nacos.api.config.annotation.NacosProperty;
import lombok.Data;

import java.util.Date;

import static com.huzi.study.nacosspring.nacos.Pojo.DATA_ID;

/**
 * @author lsx
 * @date 2020/8/4 21:11
 */
@Data
@NacosConfigurationProperties(prefix = "pojo",dataId = DATA_ID,autoRefreshed = true)
public class Pojo {

    public static final String DATA_ID = "pojo-data-id";

    private long id;

    private String name;

    private Date created;

    @NacosProperty("desc")
    private String description;

    @NacosIgnore
    private boolean ignored;
}
