package com.mayikt.controller;

import com.mayikt.entity.MemberEntity;
import com.mayikt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberCacheableController {

    @Autowired
    private UserMapper userMapper;

    /**
     * cacheNames：members文件夹，作用就是对缓存key做分类
     * 缓存key：getListMembers，key必须与方法名一一映射，key一定要加单引号，不然就会报错
     * @Cacheable 注解就可以将运行结果缓存，以后查询相同的数据，直接从缓存中取，不需要调用方法。
     * @return
     */
    @Cacheable(cacheNames = "members",key = "'getListMembers'")
    @RequestMapping("/getListMembersByCacheable")
    public List<MemberEntity> getListMembers(){
      return userMapper.findMemberAll();
    }
}
