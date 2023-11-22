package com.example.demo.repository.repository;

import com.example.demo.repository.entity.MemberSurveyEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSurveyRepository extends JpaRepository<MemberSurveyEntity, Long> {

	List<MemberSurveyEntity> findAllByVersion(double version);

	Optional<MemberSurveyEntity> findByMemberIdAndVersion(
			@Param("member_id") Long memberId, @Param("version") double version);
}
