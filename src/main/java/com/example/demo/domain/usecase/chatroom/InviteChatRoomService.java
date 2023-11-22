package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.entity.ChatRoomListEntity;
import com.example.demo.repository.entity.MemberEntity;
import com.example.demo.repository.entity.constant.ChatRoomStatus;
import com.example.demo.repository.repository.ChatRoomListRepository;
import com.example.demo.repository.repository.ChatRoomRepository;
import com.example.demo.repository.repository.MemberRepository;
import com.example.demo.web.dto.request.InviteChatRoomRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InviteChatRoomService {

	private final ChatRoomListRepository chatRoomListRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void execute(InviteChatRoomRequest request) {
		// chatRoom
		Optional<ChatRoomEntity> findChatRoom = chatRoomRepository.findById(request.getChatRoomId());
		validateExistChatRoom(findChatRoom);
		ChatRoomEntity findChatRoomEntity = findChatRoom.get();

		List<ChatRoomListEntity> allChatRooms =
				chatRoomListRepository.findAllByChatRoomId(request.getChatRoomId());

		validatePassword(request, findChatRoomEntity);
		validateHeadCount(findChatRoomEntity, allChatRooms);

		// member
		Optional<MemberEntity> findMember = memberRepository.findById(request.getMemberId());
		validateExistMember(findMember);
		MemberEntity findMemberEntity = findMember.get();

		if (allChatRooms.size() - 1 == findChatRoomEntity.getHeadcount()) {
			chatRoomListRepository.save(
					ChatRoomListEntity.builder()
							.chatRoom(findChatRoomEntity)
							.member(findMemberEntity)
							.chatStatus(ChatRoomStatus.COMPLETE)
							.build());
			return;
		}

		chatRoomListRepository.save(
				ChatRoomListEntity.builder().chatRoom(findChatRoomEntity).member(findMemberEntity).build());
	}

	private void validateExistMember(Optional<MemberEntity> findChatRoom) {
		if (!findChatRoom.isPresent()) {
			throw new IllegalArgumentException("존재하지 않는 멤버입니다.");
		}
	}

	private void validateHeadCount(
			ChatRoomEntity chatRoomEntity, List<ChatRoomListEntity> allChatRooms) {
		if (allChatRooms.size() >= chatRoomEntity.getHeadcount()) {
			throw new IllegalArgumentException("정원 초과입니다.");
		}
	}

	private void validatePassword(InviteChatRoomRequest request, ChatRoomEntity chatRoomEntity) {
		if (!chatRoomEntity.getPassword().equals(request.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
		}
	}

	private void validateExistChatRoom(Optional<ChatRoomEntity> findChatRoom) {
		if (!findChatRoom.isPresent()) {
			throw new IllegalArgumentException("존재하지 않는 방입니다.");
		}
	}
}
