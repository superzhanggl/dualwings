package com.dualwings.basic.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class SpringConfig {
	@Autowired
	private RedisTemplate redisTemplate;
	/**
	 * 修改redis的key和value序列化方式
	 * @param redisTemplate
	 */
	//@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
	    RedisSerializer stringSerializer = new StringRedisSerializer();
	    redisTemplate.setKeySerializer(stringSerializer);
	    redisTemplate.setValueSerializer(stringSerializer);
	    redisTemplate.setHashKeySerializer(stringSerializer);
	    redisTemplate.setHashValueSerializer(stringSerializer);
	    this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void getMap() {
		// 测试redis
//		ValueOperations ops = redisTemplate.opsForValue();
//		String str3 = (String) ops.get("name");
//		System.out.println("redis:"+str3);
//		System.out.println("redis:"+redisTemplate.hasKey("name"));
//		redisTemplate.boundValueOps("stingKey").set("StringValue");
//		System.out.println("redis:"+(String) ops.get("stingKey"));
	}

}
