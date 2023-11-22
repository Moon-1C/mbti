package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomSurveyQuestionEntity;
import com.example.demo.repository.repository.ChatSurveyQuestionRepository;
import com.example.demo.web.dto.response.ChatSurveyQuestionResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadChatSurveyQuestionService {

	private final ChatSurveyQuestionRepository chatSurveyQuestionRepository;

	public ChatSurveyQuestionResponse execute(double version) {
		Map<String, String> survey = new LinkedHashMap<>();

		List<ChatRoomSurveyQuestionEntity> items =
				chatSurveyQuestionRepository.findAllByVersion(version);
		for (ChatRoomSurveyQuestionEntity item : items) {
			String question = item.getQuestion();
			String selection = item.getSelection();

			survey.put(question, selection);
		}

		return ChatSurveyQuestionResponse.builder().version(version).survey(survey).build();
	}
}
