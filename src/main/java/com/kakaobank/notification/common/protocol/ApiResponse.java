package com.kakaobank.notification.common.protocol;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.kakaobank.notification.common.type.ResultType.SUCCESS;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiResponse implements Serializable {
	@Schema(description = "응답 결과 메세지. 성공 시 SUCCESS, 실패 시 에러 메시지 반환", requiredMode = REQUIRED)
	private String resultCode;

	public static ApiResponse getSuccess() {
		return new ApiResponse().setResultCode(SUCCESS.name());
	}

	public static ApiResponse getFail(String errorMessage) {
		return new ApiResponse().setResultCode(errorMessage);
	}
}
