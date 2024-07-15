package com.kakaobank.notification.repository;

import com.kakaobank.notification.model.Notification;
import com.kakaobank.notification.model.NotificationCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.kakaobank.notification.common.constants.Constants.*;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

	private final EntityManager entityManager;
	@Value("${notification.fail.count}")
	private int failCount;

	@Override
	public List<Notification> findByCriteria(NotificationCriteria criteria) {
		var typedQuery = getNotificationTypedQuery(criteria);
		return typedQuery.getResultList();
	}

	@Override
	public Page<Notification> findByCriteriaPageable(NotificationCriteria criteria, Pageable pageable) {
		var typedQuery = getNotificationTypedQuery(criteria);
		var totalRows = typedQuery.getResultList().size();

		typedQuery.setFirstResult((int)pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());

		var results = typedQuery.getResultList();

		return new PageImpl<>(results, pageable, totalRows);
	}

	private TypedQuery<Notification> getNotificationTypedQuery(NotificationCriteria criteria) {
		var criteriaBuilder = entityManager.getCriteriaBuilder();
		var query = criteriaBuilder.createQuery(Notification.class);
		var root = query.from(Notification.class);

		var predicates = buildPredicates(criteria, criteriaBuilder, root);
		query.where(predicates.toArray(new Predicate[0]));
		query.orderBy(criteriaBuilder.asc(root.get(REG_DATE)));

		return entityManager.createQuery(query);
	}

	private List<Predicate> buildPredicates(NotificationCriteria criteria, CriteriaBuilder criteriaBuilder, Root<Notification> root) {
		var predicates = new ArrayList<Predicate>();

		if (criteria.getSendStatusTypes() != null) {
			predicates.add(root.get(SEND_STATUS_TYPE).in(criteria.getSendStatusTypes()));
		}
		if (criteria.getScheduledTime() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(SCHEDULED_TIME), criteria.getScheduledTime()));
		}
		if (criteria.isCheckFailCount()) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(FAIL_COUNT), failCount));
		}
		if (criteria.getStartDate() != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(REG_DATE), criteria.getStartDate()));
		}
		if (criteria.getEndDate() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(REG_DATE), criteria.getEndDate()));
		}

		return predicates;
	}

}
