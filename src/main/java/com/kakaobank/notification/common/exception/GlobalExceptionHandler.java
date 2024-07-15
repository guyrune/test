package com.kakaobank.notification.common.exception;

import com.kakaobank.notification.common.protocol.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.kakaobank.notification.common.type.ResultType.FAIL;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ApiResponse handlerCabApiException(Exception e) {
		log.error(FAIL.name(), e);
		return ApiResponse.getFail(e.getMessage());
	}
}
