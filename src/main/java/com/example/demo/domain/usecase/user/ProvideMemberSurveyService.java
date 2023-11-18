package com.example.demo.domain.usecase.user;

import com.example.demo.domain.dto.response.ProvideQuestionInfo;
import com.example.demo.repository.entity.MemberSurveyQuestionEntity;
import com.example.demo.repository.entity.constant.MemberSurveyAnswer;
import com.example.demo.repository.repository.MemberSurveyQuestionRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProvideMemberSurveyService {

	private final double ANSWER = 0;

	private final MemberSurveyQuestionRepository memberSurveyQuestionRepository;

	public ProvideQuestionInfo execute(double version) {
		validateVersion(version);

		List<MemberSurveyQuestionEntity> allByVersion =
				memberSurveyQuestionRepository.findAllByVersion(version);
		List<String> questions = findQuestions(allByVersion);

		List<String> answerSheet = getAnswerSheet();

		return ProvideQuestionInfo.builder().questions(questions).answers(answerSheet).build();
	}

	private List<String> getAnswerSheet() {
		MemberSurveyAnswer[] values = MemberSurveyAnswer.values();
		List<String> answerSheet = new ArrayList<>();
		for (MemberSurveyAnswer now : values) {
			answerSheet.add(now.name());
		}
		return answerSheet;
	}

	private void validateVersion(double version) {
		if (version == ANSWER) {
			throw new IllegalArgumentException("잘못된 질문 버전입니다.");
		}
	}

	private List<String> findQuestions(List<MemberSurveyQuestionEntity> allByVersion) {
		List<String> questions = new ArrayList<>();
		for (MemberSurveyQuestionEntity now : allByVersion) {
			questions.add(now.getQuestion());
		}
		return questions;
	}
}
