package com.kakaobank.notification.common.queue;

import com.kakaobank.notification.model.Notification;

public interface NotificationQueue {
	void push(Notification notification);

	Notification pop();
}
