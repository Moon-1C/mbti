package com.example.demo.web.dto.request;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateChatSurveyResultRequest {

	private Long chatRoomId;
	private double version;
	private String result;
}
