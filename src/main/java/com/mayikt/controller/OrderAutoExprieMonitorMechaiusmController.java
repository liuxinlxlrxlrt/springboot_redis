package com.mayikt.controller;

import com.mayikt.entity.OrderEntity;
import com.mayikt.mapper.OrderMapper;
import com.mayikt.utils.RedisJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * expire[[ɪkˈspaɪər]](expiration [ˌekspəˈreɪʃn] ):过期
 * monitor [ˈmɑːnɪtər]：监听
 * mechanism [ˈmekənɪzəm] ：机制
 * （Automatic expiration monitoring mechanism）
 */
@RestController
public class OrderAutoExprieMonitorMechaiusmController {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisJsonUtils redisJsonUtils;

    @RequestMapping("/addOrder")
    public String addOrder(){
        //1、提前生成订单token 临时且唯一
        String orderToken = UUID.randomUUID().toString();
        //2、生成订单id
        Long orderId= System.currentTimeMillis();
        //3、将token到redis中
        redisJsonUtils.setString(orderToken,orderId+"",10L);
        //4、初始化OrderEntity对象
        OrderEntity orderEntity=new OrderEntity( null,"蚂蚁课堂九九会员",orderId+"",orderToken);
        //5、存放到数据库中
        return orderMapper.insertOrder(orderEntity)>0?"success":"fail";
    }
}
