package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.entity.ChatRoomListEntity;
import com.example.demo.repository.repository.ChatRoomListRepository;
import com.example.demo.web.dto.response.ChatRoomInfo;
import com.example.demo.web.dto.response.ChatRoomResponse;
import java.util.ArrayList;
import java.util.List;
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
		List<ChatRoomInfo> chatRoomInfos = new ArrayList<>();

		for (ChatRoomListEntity chatRoomListEntity : chatRoomList) {
			ChatRoomEntity chatRoom = chatRoomListEntity.getChatRoom();
			chatRoomInfos.add(generateChatRoomInfo(chatRoomListEntity, chatRoom));
		}

		return ChatRoomResponse.builder().chatRoomInfos(chatRoomInfos).build();
	}

	private ChatRoomInfo generateChatRoomInfo(
			ChatRoomListEntity chatRoomListEntity, ChatRoomEntity chatRoom) {
		return ChatRoomInfo.builder()
				.title(chatRoom.getTitle())
				.chatroomId(chatRoom.getId())
				.chatroomStatus(chatRoomListEntity.getChatStatus())
				.build();
	}
}
