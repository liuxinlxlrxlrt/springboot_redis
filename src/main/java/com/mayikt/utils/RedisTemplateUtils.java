package com.mayikt.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisTemplateUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setObject(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    public Object getObjet(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
