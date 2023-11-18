package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.entity.ChatRoomListEntity;
import com.example.demo.repository.entity.constant.ChatRoomStatus;
import com.example.demo.repository.repository.ChatRoomListRepository;
import com.example.demo.web.dto.response.ChatRoomResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadChatRoomService {

	private final ChatRoomListRepository chatRoomListRepository;

	public ChatRoomResponse execute(Long memberId) {
		List<ChatRoomListEntity> chatRoomList = chatRoomListRepository.findAllByMemberId(memberId);
		return generateChatRoomResponse(chatRoomList);
	}

	private ChatRoomResponse generateChatRoomResponse(List<ChatRoomListEntity> chatRoomList) {
		Map<Long, ChatRoomStatus> chatRooms = new LinkedHashMap<>();

		for (ChatRoomListEntity chatRoomListEntity : chatRoomList) {
			ChatRoomEntity chatRoom = chatRoomListEntity.getChatRoom();
			chatRooms.put(chatRoom.getId(), chatRoom.getStatus());
		}

		return ChatRoomResponse.builder().chatRooms(chatRooms).build();
	}
}
