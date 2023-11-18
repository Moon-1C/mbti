package com.example.demo.repository.repository;

import com.example.demo.repository.entity.ChatRoomSurveyQuestionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatSurveyQuestionRepository
		extends JpaRepository<ChatRoomSurveyQuestionEntity, Long> {

	List<ChatRoomSurveyQuestionEntity> findAllByVersion(double version);
}
