package com.assignment.scheduler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.assignment.service.NotificationService;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 300000)
    public void sweepNotifications() {

        Set<String> users = notificationService.getPendingUsers();

        if (users == null || users.isEmpty()) {
            return;
        }

        for (String key : users) {

            Long count = notificationService.getNotificationCount(key);

            String firstMessage =
                    notificationService.popFirstNotification(key);

            if (count != null && count > 0) {

                System.out.println(
                    "Summarized Push Notification: "
                    + firstMessage
                    + " and "
                    + (count - 1)
                    + " others interacted with your posts."
                );
            }

            notificationService.deleteKey(key);
        }
    }
}