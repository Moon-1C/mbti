package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomSurveyQuestionEntity;
import com.example.demo.repository.repository.ChatSurveyQuestionRepository;
import com.example.demo.web.dto.response.ChatSurveyQuestionResponse;
import java.util.HashMap;
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
		Map<String, String> data = new HashMap<>();

		List<ChatRoomSurveyQuestionEntity> items =
				chatSurveyQuestionRepository.findAllByVersion(version);
		for (ChatRoomSurveyQuestionEntity item : items) {
			String question = item.getQuestion();
			String selection = item.getSelection();

			data.put(question, selection);
		}

		return ChatSurveyQuestionResponse.builder().version(version).data(data).build();
	}
}
