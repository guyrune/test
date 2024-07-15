package com.kakaobank.notification.controller.protocol.send;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.kakaobank.notification.common.constants.Constants.PHONE_NUMBER;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SmsProtocol extends Message {
	private String phoneNumber;

	public SmsProtocol(Map<String, String> request) {
		super(request);
		this.phoneNumber = request.get(PHONE_NUMBER);
	}

	@Override
	public void validation() {
		if (StringUtils.isBlank(phoneNumber)) {
			throw new IllegalArgumentException("phoneNumber is empty");
		}
	}
}
