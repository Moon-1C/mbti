package com.example.demo.repository.entity.constant;

public enum MemberSurveyAnswer {
	STRONGLY_AGREE(5),
	AGREE(4),
	NEURAL(3),
	DISAGREE(2),
	STRONGLY_DISAGREE(1),
	;

	private final int point;

	MemberSurveyAnswer(int point) {
		this.point = point;
	}
}
