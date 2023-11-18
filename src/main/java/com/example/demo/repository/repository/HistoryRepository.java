package com.example.demo.repository.repository;

import com.example.demo.repository.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {}
