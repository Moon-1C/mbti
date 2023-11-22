package com.example.demo.domain.usecase.spot;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.entity.SpotEntity;
import com.example.demo.repository.repository.ChatRoomRepository;
import com.example.demo.repository.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateSpotService {

	private final SpotRepository spotRepository;
	private final ChatRoomRepository chatRoomRepository;

	@Transactional
	public Long execute(String spot, Long chatroomId) {
		return spotRepository
				.save(SpotEntity.builder().spot(spot).chatRoom(getChatRoom(chatroomId)).build())
				.getId();
	}

	private ChatRoomEntity getChatRoom(Long chatroomId) {
		ChatRoomEntity chatRoom =
				chatRoomRepository
						.findById(chatroomId)
						.orElseThrow(() -> new IllegalArgumentException("해당 채팅방은 존재하지 않습니다."));
		return chatRoom;
	}
}
