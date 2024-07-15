package com.kakaobank.notification.service;

import com.kakaobank.notification.common.protocol.ApiResponse;
import com.kakaobank.notification.common.queue.NotificationQueue;
import com.kakaobank.notification.common.type.SendStatusType;
import com.kakaobank.notification.model.Notification;
import com.kakaobank.notification.model.NotificationCriteria;
import com.kakaobank.notification.repository.NotificationRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.kakaobank.notification.common.constants.Constants.THREAD_POOL_COUNT;
import static com.kakaobank.notification.common.type.SendStatusType.*;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class NotificationSendService {
	private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
	private final NotificationQueue notificationQueue;
	private final NotificationRepository notificationRepository;
	private final RestTemplate restTemplate;

	@PostConstruct
	public void init() {
		for (int i = 0; i < THREAD_POOL_COUNT; i++) {
			executorService.submit(this::processNotifications);
		}
	}

	public void addNotificationQueue(Notification notification) {
		notificationQueue.push(notification);
		notificationRepository.save(notification);
	}

	@Scheduled(fixedRate = 60000) // 1분마다 실행
	public void processPendingNotifications() {
		var pendingNotifications = notificationRepository.findByCriteria(new NotificationCriteria().setSendStatusTypes(List.of(READY, FAIL))
																								   .setScheduledTime(LocalDateTime.now())
																								   .setCheckFailCount(true));
		if (CollectionUtils.isNotEmpty(pendingNotifications)) {
			pendingNotifications.forEach(notification -> {
				notificationQueue.push(notification);
				notification.setSendStatusType(PROCESSING);
			});
			notificationRepository.saveAll(pendingNotifications);
		}
	}

	private void processNotifications() {
		while (true) {
			var notification = notificationQueue.pop();
			if (notification != null) {
				var sendStatusType = sendNotification(notification);
				notification.setSendStatusType(sendStatusType);
				if (sendStatusType != SUCCESS) {
					notification.addFailCount();
				}
				notificationRepository.save(notification);
			}
		}
	}

	private SendStatusType sendNotification(Notification notification) {
		var uri = notification.getNotificationType().name().toLowerCase();
		var url = "http://localhost:8090/send/" + uri;

		var headers = new HttpHeaders();
		headers.set("Content-Type", "application/json;charset=UTF-8");

		var entity = new HttpEntity<>(notification.getMessage(), headers);

		try {
			var response = restTemplate.exchange(url, HttpMethod.POST, entity, ApiResponse.class);
			return SendStatusType.getSendStatusType(response.getBody().getResultCode());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FAIL;
		}
	}
}
