package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.entity.HistoryEntity;
import com.example.demo.repository.entity.SpotEntity;
import com.example.demo.repository.repository.ChatRoomRepository;
import com.example.demo.repository.repository.HistoryRepository;
import com.example.demo.repository.repository.SpotRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaveHistoryService {

	private final ChatRoomRepository chatRoomRepository;
	private final SpotRepository spotRepository;
	private final HistoryRepository historyRepository;

	@Transactional
	public void execute(Long chatroomId, Long memberId) {
		Optional<ChatRoomEntity> findChatRoom = chatRoomRepository.findById(chatroomId);
		validateChatRoom(findChatRoom);
		ChatRoomEntity chatRoomEntity = findChatRoom.get();

		validateManager(memberId, chatRoomEntity);

		validateComplete(chatRoomEntity);

		List<SpotEntity> allSpots = spotRepository.findAllByChatRoomId(chatroomId);
		String spots = allSpots.stream().map(SpotEntity::getSpot).collect(Collectors.joining(","));
		historyRepository.save(
				new HistoryEntity()
						.toBuilder()
								.history(spots)
								.createDate(
										Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
								.build());
	}

	private static void validateComplete(ChatRoomEntity chatRoomEntity) {
		if (!chatRoomEntity.getIsComplete()) {
			throw new IllegalArgumentException("모두가 동의하지 않아 여행지를 저장할 수 없습니다. ");
		}
	}

	private static void validateManager(Long memberId, ChatRoomEntity chatRoomEntity) {
		if (chatRoomEntity.getManager().getId() != memberId) {
			throw new IllegalArgumentException("방장이 아닙니다.");
		}
	}

	private void validateChatRoom(Optional<ChatRoomEntity> findChatRoom) {
		if (!findChatRoom.isPresent()) {
			throw new IllegalArgumentException("없는 채팅방입니다.");
		}
	}
}
