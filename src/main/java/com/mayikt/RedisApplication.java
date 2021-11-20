package com.mayikt;

import com.sun.org.apache.regexp.internal.RE;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableCaching
@MapperScan("com.mayikt.mapper")
public class RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	//删除RedisTemplate的@Bean
//	@Bean
//	public RedisTemplate redisTemplate(){
//		return new RedisTemplate();
//	}

}
