package com.example.demo.domain.usecase.chatroom;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.entity.ChatRoomListEntity;
import com.example.demo.repository.entity.MemberEntity;
import com.example.demo.repository.repository.ChatRoomListRepository;
import com.example.demo.repository.repository.ChatRoomRepository;
import com.example.demo.repository.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateChatRoomListService {

	private final ChatRoomListRepository chatRoomListRepository;
	private final MemberRepository memberRepository;
	private final ChatRoomRepository chatRoomRepository;

	@Transactional
	public Long create(Long memberId, Long chatroomId) {

		ChatRoomEntity chatroom = chatRoomRepository.findById(chatroomId).get();
		MemberEntity member = memberRepository.findById(memberId).get();

		return chatRoomListRepository
				.save(ChatRoomListEntity.builder().member(member).chatRoom(chatroom).build())
				.getId();
	}
}
