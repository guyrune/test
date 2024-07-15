package com.kakaobank.notification.service;

import com.kakaobank.notification.model.Notification;
import com.kakaobank.notification.model.NotificationCriteria;
import com.kakaobank.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
@RequiredArgsConstructor
public class NotificationHistoryService {
	private final NotificationRepository notificationRepository;

	public Page<Notification> getNotificationHistoryByCriteriaPageable(NotificationCriteria criteria, Pageable pageable) {
		return notificationRepository.findByCriteriaPageable(criteria, pageable);
	}

	@Scheduled(cron = "0 0 1 * * ?") // 매일 01시 00분에 실행
	public void deleteNotifications() {
		var targetDate = LocalDateTime.now().minusMonths(3);
		notificationRepository.deleteNotificationByRegDateBefore(targetDate);
	}

}
