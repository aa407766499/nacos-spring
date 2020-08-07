package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author lsx
 * @date 2020/8/4 21:15
 */
//@Configuration
@Slf4j
public class PojoNacosConfigListener {

    public static final String POJO_DATA_ID = "pojo-data-id";

    private final Pojo pojo = new Pojo();

    @NacosInjected
    private ConfigService configService;

    @PostConstruct
    public void init() throws Exception {
        pojo.setId(1L);
        pojo.setName("mercyblitz");
        pojo.setCreated(new Date());
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(pojo);
        configService.publishConfig(POJO_DATA_ID, "DEFAULT_GROUP", content);
    }

    @NacosConfigListener(dataId = POJO_DATA_ID, converter = PojoNacosConfigConverter.class)
    public void onReceived(Pojo value) {
        log.info("onReceived(Pojo)：{}", value);
    }

}
