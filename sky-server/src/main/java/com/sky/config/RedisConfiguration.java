package com.sky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 1:54 PM
 */
@Configuration
public class RedisConfiguration {
    // 注入 RedisTemplate
     @Bean
     public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
         RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
         redisTemplate.setConnectionFactory(redisConnectionFactory);
         // 设置序列化方式
         redisTemplate.setKeySerializer(new StringRedisSerializer());
//         redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//         redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//         redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//         redisTemplate.afterPropertiesSet();
         return redisTemplate;
     }
}
