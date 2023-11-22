package com.example.demo.repository.repository;

import com.example.demo.repository.entity.SpotEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<SpotEntity, Long> {
	List<SpotEntity> findAllByChatRoomId(Long id);
}
