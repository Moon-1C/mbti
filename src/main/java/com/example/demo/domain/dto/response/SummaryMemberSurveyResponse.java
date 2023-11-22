package com.example.demo.domain.dto.response;

import java.util.List;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SummaryMemberSurveyResponse {

	private List<String> surveys;
}
