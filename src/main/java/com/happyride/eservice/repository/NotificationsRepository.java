package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Notifications;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    Optional<Notifications> findById(long id);
    List<Notifications> findByNotificationReceiver(Users users);

    List<Notifications> findByNotificationSeenStatusContaining(Boolean status);

}
