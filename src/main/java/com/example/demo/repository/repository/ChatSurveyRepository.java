package com.example.demo.repository.repository;

import com.example.demo.repository.entity.ChatSurveyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSurveyRepository extends JpaRepository<ChatSurveyEntity, Long> {

	List<ChatSurveyEntity> findAllByChatRoomId(Long id);
}
