package com.kakaobank.notification.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
	// Constants for NotificationProtocol
	public static final String TITLE = "title";
	public static final String CONTENTS = "contents";
	public static final String NOTIFICATION_TYPE = "notificationType";
	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String TALK_ID = "talkId";

	// Constants for Notification Criteria Field
	public static final String SEND_STATUS_TYPE = "sendStatusType";
	public static final String SCHEDULED_TIME = "scheduledTime";
	public static final String FAIL_COUNT = "failCount";
	public static final String REG_DATE = "regDate";

	//ETC..
	public static final String DATE_FORMAT = "yyyyMMddHHmm";
	public static final int THREAD_POOL_COUNT = 5;
}
