package com.pcdd.redissonexample;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private String port;
    @Value("${spring.data.redis.password}")
    private String pwd;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单实例模式
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", host, port))
                .setPassword(pwd);
        RedissonClient redisson = Redisson.create(config);
        System.out.println("redisson = " + redisson);
        return redisson;
    }

}
