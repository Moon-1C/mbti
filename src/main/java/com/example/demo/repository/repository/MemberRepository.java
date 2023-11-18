package com.example.demo.repository.repository;

import com.example.demo.repository.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByName(String name);

	Optional<MemberEntity> findByNameAndDeletedFalse(String name);

	Optional<MemberEntity> findByIdAndDeletedFalse(Long Id);
}
