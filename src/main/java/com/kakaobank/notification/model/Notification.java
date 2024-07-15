package com.kakaobank.notification.model;

import com.kakaobank.notification.common.type.NotificationType;
import com.kakaobank.notification.common.type.SendStatusType;
import com.kakaobank.notification.common.util.ObjectMapperUtil;
import com.kakaobank.notification.controller.protocol.send.Message;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static com.kakaobank.notification.common.constants.Constants.DATE_FORMAT;

@Data
@Entity
@NoArgsConstructor
@Table(indexes = {
	@Index(name = "idx_regDate", columnList = "regDate"),
	@Index(name = "idx_scheduledTime_sendType", columnList = "scheduledTime, sendType")
})
public class Notification {
	@Id
	@Column(length = 36)
	private String uuid;
	@Column(columnDefinition = "TEXT")
	private String message;
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
	@Enumerated(EnumType.STRING)
	private SendStatusType sendStatusType;
	private int failCount;
	private LocalDateTime scheduledTime;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;

	public Notification(Message message) {
		this.uuid = UUID.randomUUID().toString();
		this.notificationType = message.getNotificationType();
		this.message = ObjectMapperUtil.toJsonString(message);
		this.sendStatusType = message.getScheduledTime() == null ? SendStatusType.PROCESSING : SendStatusType.READY;
		this.failCount = 0;
		this.scheduledTime = Optional.ofNullable(message.getScheduledTime())
									 .map(time -> LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATE_FORMAT)))
									 .orElse(LocalDateTime.now());

	}

	public void addFailCount() {
		this.failCount++;
	}

	@PrePersist
	public void prePersist() {
		this.regDate = LocalDateTime.now();
	}

}
