package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Notifications;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationsService {

    private final NotificationsRepository notificationsRepository;

    @Autowired
    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public Optional<Notifications> findNotificationById(long id) {
        return notificationsRepository.findById(id);
    }

    public List<Notifications> findNotificationByUsers(Users users) {
        return notificationsRepository.findByNotificationReceiver(users);
    }

    public void saveNotifications(Notifications notifications) {
        notificationsRepository.save(notifications);
    }

    public List<Notifications> findNewNotification(Boolean unseen) {

        return notificationsRepository.findByNotificationSeenStatusContaining(unseen);
    }

}
