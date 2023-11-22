package com.example.demo.domain.dto.response;

import java.util.Map;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SummaryChatSurveyResponse {

	/** key: question value: selections */
	private Map<String, String> surveys;
}
