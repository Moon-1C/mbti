package com.example.demo.repository.repository;

import com.example.demo.repository.entity.ChatRoomListEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomListRepository extends JpaRepository<ChatRoomListEntity, Long> {

	List<ChatRoomListEntity> findAllByMemberId(Long memberId);

	List<ChatRoomListEntity> findAllByChatRoomId(Long chatRoomId);
}
