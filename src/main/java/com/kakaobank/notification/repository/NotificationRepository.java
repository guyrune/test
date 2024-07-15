package com.kakaobank.notification.repository;

import com.kakaobank.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {
	void deleteNotificationByRegDateBefore(LocalDateTime targetDate);
}
