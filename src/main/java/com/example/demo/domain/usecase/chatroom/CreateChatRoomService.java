package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.repository.ChatRoomRepository;
import com.example.demo.web.dto.request.CreateChatRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	@Transactional
	public Long execute(CreateChatRoomRequest request) {

		return chatRoomRepository
				.save(
						ChatRoomEntity.builder()
								.title(request.getTitle())
								.password(request.getPassword())
								.spot(request.getSpot())
								.headcount(request.getHeadcount())
								.start(request.getStart())
								.end(request.getEnd())
								.build())
				.getId();
	}
}
