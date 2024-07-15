package com.kakaobank.notification.controller.protocol.send;

import com.kakaobank.notification.common.type.NotificationType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static com.kakaobank.notification.common.constants.Constants.*;

@Data
public abstract class Message {
	private String title;
	private String contents;
	private String scheduledTime;
	private NotificationType notificationType;

	protected Message(Map<String, String> request) {
		this.title = request.get(TITLE);
		this.contents = request.get(CONTENTS);
		this.scheduledTime = request.get(SCHEDULED_TIME);
		this.notificationType = NotificationType.getNotificationType(request.get(NOTIFICATION_TYPE));
		checkScheduledTime();
	}

	private void checkScheduledTime() {
		if (StringUtils.isNotBlank(scheduledTime)) {
			try {
				LocalDateTime.parse(scheduledTime, DateTimeFormatter.ofPattern(DATE_FORMAT));
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}

	public abstract void validation();
}
