package com.huzi.study.nacosspring.nacos;

import com.alibaba.nacos.api.config.convert.NacosConfigConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lsx
 * @date 2020/8/4 21:21
 */
public class PojoNacosConfigConverter implements NacosConfigConverter<Pojo> {

    @Override
    public boolean canConvert(Class<Pojo> targetType) {
        return true;
    }

    @Override
    public Pojo convert(String config) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(config, Pojo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
