package com.kakaobank.notification.controller.protocol.send;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.kakaobank.notification.common.constants.Constants.EMAIL_ADDRESS;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class EmailProtocol extends Message {

	private String emailAddress;

	public EmailProtocol(Map<String, String> request) {
		super(request);
		this.emailAddress = request.get(EMAIL_ADDRESS);
	}

	@Override
	public void validation() {
		if (StringUtils.isBlank(emailAddress)) {
			throw new IllegalArgumentException("emailAddress is empty");
		}
	}
}
