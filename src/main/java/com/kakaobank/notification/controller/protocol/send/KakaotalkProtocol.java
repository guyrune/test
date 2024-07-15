package com.kakaobank.notification.controller.protocol.send;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.kakaobank.notification.common.constants.Constants.TALK_ID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class KakaotalkProtocol extends Message {
	private String talkId;

	public KakaotalkProtocol(Map<String, String> request) {
		super(request);
		this.talkId = request.get(TALK_ID);
	}

	@Override
	public void validation() {
		if (StringUtils.isBlank(talkId)) {
			throw new IllegalArgumentException("talkId is empty");
		}
	}
}
