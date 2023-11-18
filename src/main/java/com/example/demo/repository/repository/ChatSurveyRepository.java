package com.example.demo.repository.repository;

import com.example.demo.repository.entity.ChatSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSurveyRepository extends JpaRepository<ChatSurveyEntity, Long> {}
