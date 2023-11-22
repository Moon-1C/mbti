package com.example.demo.domain.util;

import com.example.demo.domain.dto.response.SummaryChatSurveyResponse;
import com.example.demo.domain.usecase.chatroom.ReadChatSurveyQuestionService;
import com.example.demo.repository.repository.ChatSurveyRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GptChatSurveyGenerator {

	private final ReadChatSurveyQuestionService readChatSurveyQuestionService;
	private final ChatSurveyRepository chatSurveyRepository;

	public SummaryChatSurveyResponse execute(Long chatroomId, double version) {
		Map<String, String> surveys = new LinkedHashMap<>();
		Set<String> questions = getQuestions(version);
		List<String> selections = getSelections(chatroomId);

		// 각 질문에 대해 반복
		int index = 0;
		for (String question : questions) {
			StringBuilder responseBuilder = new StringBuilder();

			// 각 응답에 대해 반복
			for (String selection : selections) {
				String[] responses = selection.split(",");
				responseBuilder.append(responses[index]);
				if (selection != selections.get(selections.size() - 1)) {
					responseBuilder.append(",");
				}
			}

			surveys.put(question, responseBuilder.toString());
			index++;
		}

		return SummaryChatSurveyResponse.builder().surveys(surveys).build();
	}

	/** 버전에 맞는 질문 항목들을 가져옴 */
	private Set<String> getQuestions(double version) {
		return readChatSurveyQuestionService.execute(version).getSurvey().keySet();
	}

	private List<String> getSelections(Long chatroomId) {
		List<String> selections = new ArrayList<>();
		chatSurveyRepository.findAllByChatRoomId(chatroomId).stream()
				.forEach((chatSurvey) -> selections.add(chatSurvey.getAnswer()));

		return selections;
	}
}
