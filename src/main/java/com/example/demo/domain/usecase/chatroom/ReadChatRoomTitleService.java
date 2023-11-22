package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadChatRoomTitleService {

	private final ChatRoomRepository chatRoomRepository;

	@Transactional(readOnly = true)
	public String execute(Long id) {
		return chatRoomRepository
				.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Error!"))
				.getTitle();
	}
}
