package com.mayikt.controller;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.entity.UserEntity;
import com.mayikt.utils.RedisBinaryUtils;
import com.mayikt.utils.RedisJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisJsonAndBinaryController {

    @Autowired
    private RedisJsonUtils redisUtils;

    @Autowired
    private RedisBinaryUtils redisBinaryUtils;


    /**
     * 方式2：直接使用redis方式存储对象
     * @param userEntity
     * @return
     */
    @GetMapping("/addUserByBinary")
    public String addUserByBinary(UserEntity userEntity){
        redisBinaryUtils.setObject("userEntity",userEntity);
        return "二进制方式存储成功";
    }

    @RequestMapping("/getUserByBinary")
    public UserEntity getUserByBinary(String key){
        return (UserEntity) redisBinaryUtils.getObject(key);
    }




    /**
     * 方式1：redis存放一个对象，使用json序列化与饭序列化
     * @param userEntity
     * @return
     */
    @GetMapping("/addUserByJson")
    public String addUserByJson(UserEntity userEntity){
        String json =JSONObject.toJSONString(userEntity);
        redisUtils.setString("userEntity",json);
        return "存储成功";
    }

    @RequestMapping("/getUserByJson")
    public UserEntity getUserByJson(String key){
        String json = redisUtils.getString(key);
        UserEntity userEntity = JSONObject.parseObject(json, UserEntity.class);
        return userEntity;
    }
}
