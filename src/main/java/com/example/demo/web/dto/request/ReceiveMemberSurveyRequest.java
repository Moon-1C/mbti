package com.example.demo.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMemberSurveyRequest {

	private int surveyVersion;
	private String surveyResult;
}
