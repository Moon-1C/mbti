package com.example.demo.repository.repository;

import com.example.demo.repository.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByName(String name);

    Optional<MemberEntity> findByNameAndDeletedFalse(String name);

    Optional<MemberEntity> findByIdAndDeletedFalse(Long Id);

}
