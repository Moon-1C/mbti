package com.example.demo.repository.repository;

import com.example.demo.repository.entity.ChatRoomListEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomListRepository extends JpaRepository<ChatRoomListEntity, Long> {

	List<ChatRoomListEntity> findAllByMemberId(Long id);

	Optional<ChatRoomListEntity> findByMemberIdAndChatRoomId(
			@Param("member_id") Long memberId, @Param("chat_room_id") Long chatroomId);

	List<ChatRoomListEntity> findAllByChatRoomId(Long id);
}
