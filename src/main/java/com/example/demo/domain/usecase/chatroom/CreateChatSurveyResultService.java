package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatSurveyEntity;
import com.example.demo.repository.repository.ChatSurveyRepository;
import com.example.demo.web.dto.request.CreateChatSurveyResultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateChatSurveyResultService {

	private final ChatSurveyRepository chatSurveyRepository;

	@Transactional
	public Long save(CreateChatSurveyResultRequest request) {

		return chatSurveyRepository
				.save(
						ChatSurveyEntity.builder()
								.version(request.getVersion())
								.answer(request.getResult())
								.build())
				.getId();
	}
}
