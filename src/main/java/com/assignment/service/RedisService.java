package com.assignment.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Long incrementVirality(Long postId, int points) {

        String key = "post:" + postId + ":virality_score";

        return redisTemplate.opsForValue().increment(key, points);
    }

    public String getVirality(Long postId) {

        String key = "post:" + postId + ":virality_score";

        return redisTemplate.opsForValue().get(key);
    }

    public boolean setCooldown(String key, long minutes) {

        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "LOCKED", Duration.ofMinutes(minutes));

        return success != null && success;
    }

    public boolean exists(String key) {
        Boolean result = redisTemplate.hasKey(key);
        return result != null && result;
    }

    public Long incrementCounter(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
}