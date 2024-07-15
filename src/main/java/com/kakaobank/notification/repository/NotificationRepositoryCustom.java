package com.kakaobank.notification.repository;

import com.kakaobank.notification.model.Notification;
import com.kakaobank.notification.model.NotificationCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepositoryCustom {
	List<Notification> findByCriteria(NotificationCriteria criteria);

	Page<Notification> findByCriteriaPageable(NotificationCriteria criteria, Pageable pageable);

}
