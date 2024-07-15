package com.kakaobank.notification.model;

import com.kakaobank.notification.common.type.SendStatusType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class NotificationCriteria {
	private List<SendStatusType> sendStatusTypes;
	private LocalDateTime scheduledTime;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isCheckFailCount;
}
