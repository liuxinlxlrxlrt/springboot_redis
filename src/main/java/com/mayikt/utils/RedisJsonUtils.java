package com.mayikt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisJsonUtils {

    /**
     * 获取Redis模板
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key,String value){
        setString(key,value,null);
    }

    public void setString(String key,String value,Long timeOut){

        stringRedisTemplate.opsForValue().set(key,value);

        if(timeOut !=null){
            stringRedisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
        }
    }

    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * redis当成数据库用，不设置有效期，每次查询都会放到redis中，
     * redis默认会开启数据持久化机制，会导致某一天redis崩溃
     *
     * 注意事项：对我们的redis的key设置一个有效期
     */
}
