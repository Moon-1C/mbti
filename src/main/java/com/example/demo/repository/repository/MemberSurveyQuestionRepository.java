package com.example.demo.repository.repository;

import com.example.demo.repository.entity.MemberSurveyQuestionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSurveyQuestionRepository
		extends JpaRepository<MemberSurveyQuestionEntity, Long> {

	List<MemberSurveyQuestionEntity> findAllByVersion(double version);

	MemberSurveyQuestionEntity findByVersion(double version);
}
