package com.kakaobank.notification.controller;

import com.kakaobank.notification.common.protocol.ApiResponse;
import com.kakaobank.notification.common.type.NotificationType;
import com.kakaobank.notification.controller.protocol.history.HistoryProtocol;
import com.kakaobank.notification.controller.protocol.send.EmailProtocol;
import com.kakaobank.notification.controller.protocol.send.KakaotalkProtocol;
import com.kakaobank.notification.controller.protocol.send.SmsProtocol;
import com.kakaobank.notification.model.Notification;
import com.kakaobank.notification.service.NotificationHistoryService;
import com.kakaobank.notification.service.NotificationSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.kakaobank.notification.common.constants.Constants.NOTIFICATION_TYPE;

@Tag(name = "Notification", description = "알림 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationSendService notificationSendService;
	private final NotificationHistoryService notificationHistoryService;

	@Operation(summary = "알림발송등록 API")
	@PostMapping("/send")
	public ApiResponse sendNotification(@RequestBody Map<String, String> request) {
		var notificationType = NotificationType.getNotificationType(request.get(NOTIFICATION_TYPE));
		var message = switch (notificationType) {
			case SMS -> new SmsProtocol(request);
			case EMAIL -> new EmailProtocol(request);
			case KAKAOTALK -> new KakaotalkProtocol(request);
		};

		message.validation();

		notificationSendService.addNotificationQueue(new Notification(message));

		return ApiResponse.getSuccess();
	}

	@Operation(summary = "알림내역조회 API")
	@GetMapping("/history")
	public HistoryProtocol.Response getNotificationHistory(@ParameterObject HistoryProtocol.Request request,
														   @ParameterObject Pageable pageable) {
		var criteria = request.convertToCriteria();
		var histories = notificationHistoryService.getNotificationHistoryByCriteriaPageable(criteria, pageable);

		return new HistoryProtocol.Response().convertFrom(histories);
	}
}
