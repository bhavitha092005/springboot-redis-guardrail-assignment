package com.assignment.service;

import java.time.Duration;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void handleBotInteraction(Long userId, Long botId) {

        String cooldownKey = "user:" + userId + ":notif_cooldown";

        String message = "Bot " + botId + " replied to your post";

        Boolean exists = redisTemplate.hasKey(cooldownKey);

        if (exists != null && exists) {

            String listKey = "user:" + userId + ":pending_notifs";

            redisTemplate.opsForList().rightPush(listKey, message);

        } else {

            System.out.println("Push Notification Sent to User " + userId + ": " + message);

            redisTemplate.opsForValue().set(
                    cooldownKey,
                    "LOCKED",
                    Duration.ofMinutes(15));
        }
    }

    public Set<String> getPendingUsers() {
        return redisTemplate.keys("user:*:pending_notifs");
    }

    public Long getNotificationCount(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public String popFirstNotification(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}