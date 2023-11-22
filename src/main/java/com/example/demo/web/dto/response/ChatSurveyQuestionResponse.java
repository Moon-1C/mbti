package com.example.demo.web.dto.response;

import java.util.Map;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChatSurveyQuestionResponse {

	private double version;

	/** key: 질문 텍스트 value: 선택 항목 텍스트 - ex) 만족,보통,불만족 */
	private Map<String, String> survey;
}
