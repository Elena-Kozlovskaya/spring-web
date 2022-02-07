package com.geekbrains.spring.web.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories // для создание репозитриев рэдиса
public class RedisConfig {
    @Bean
    // фабрика поясняющая, как подключаться к рэдису(если изменить дефолтные порты, приписывать все сюда)
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        /*factory.getPoolConfig().setMaxIdle(20);
        factory.getPoolConfig().setMinIdle(10);*/
        return factory;
    }

    @Bean
    //RedisTemplate отправка и получение запросов (k, v) (string, json)
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
