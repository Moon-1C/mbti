package com.example.demo.repository.repository;

import com.example.demo.repository.entity.MemberSurveyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSurveyRepository extends JpaRepository<MemberSurveyEntity, Long> {

	List<MemberSurveyEntity> findAllByVersion(double version);
}
