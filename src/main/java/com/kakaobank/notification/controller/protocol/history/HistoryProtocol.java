package com.kakaobank.notification.controller.protocol.history;

import com.kakaobank.notification.common.protocol.ApiResponse;
import com.kakaobank.notification.model.Notification;
import com.kakaobank.notification.model.NotificationCriteria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

import static com.kakaobank.notification.common.type.ResultType.SUCCESS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryProtocol {
	@Data
	@Accessors(chain = true)
	public static class Request {
		@Schema(description = "등록일-시작일시(yyyy-MM-dd)")
		private LocalDate startDate;
		@Schema(description = "등록일-종료일시(yyyy-MM-dd)")
		private LocalDate endDate;

		public NotificationCriteria convertToCriteria() {
			return new NotificationCriteria().setStartDate(startDate)
											 .setEndDate(endDate);
		}
	}

	@Getter
	@Setter
	@Accessors(chain = true)
	@EqualsAndHashCode(callSuper = true)
	public static class Response extends ApiResponse {
		@Schema(description = "범위 총 개수")
		private long totalItems;
		@Schema(description = "범위 목록")
		private List<Notification> notifications;

		public Response convertFrom(Page<Notification> notifications) {
			super.setResultCode(SUCCESS.name());
			this.totalItems = notifications.getTotalElements();
			this.notifications = notifications.getContent();
			return this;
		}
	}
}
