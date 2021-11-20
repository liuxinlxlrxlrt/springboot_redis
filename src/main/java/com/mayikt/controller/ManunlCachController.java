package com.mayikt.controller;

import com.mayikt.entity.OrderEntity;
import com.mayikt.mapper.OrderMapper;
import com.mayikt.utils.RedisTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * manual cach缓存手册
 */
@RestController
public class ManunlCachController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @GetMapping("/getOrder")
    public Object getMOrder(String orderId){

        OrderEntity orderEntity = (OrderEntity) redisTemplateUtils.getObjet(orderId);

        if (orderEntity!=null){
            System.out.println("从缓存中查询到数据，返回缓存中的数据");
            return  orderEntity;
        }
        //Redis不存在数据，查询数据库
        System.out.println("开始从数据库中查询到数据");
        OrderEntity orderDbEntity =  orderMapper.getByOrderId(orderId);
        if (orderDbEntity!=null){
            System.out.println("从数据库查询到数据放到缓存中");
            redisTemplateUtils.setObject(orderId,orderDbEntity);
            return orderDbEntity;
        }
        return null;
    }

}
