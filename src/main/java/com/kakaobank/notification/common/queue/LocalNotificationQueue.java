package com.kakaobank.notification.common.queue;

import com.kakaobank.notification.model.Notification;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class LocalNotificationQueue implements NotificationQueue {

	private final Queue<Notification> queue = new ConcurrentLinkedQueue<>();

	@Override
	public void push(Notification notification) {
		queue.offer(notification);
	}

	@Override
	public Notification pop() {
		return queue.poll();
	}
}
