package com.example.demo.domain.usecase.history;

import com.example.demo.domain.dto.response.ProvideHistoryInfo;
import com.example.demo.repository.entity.HistoryEntity;
import com.example.demo.repository.repository.HistoryRepository;
import com.example.demo.web.dto.response.HistoryResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProvideHistoryService {

	private final HistoryRepository historyRepository;

	public HistoryResponse execute() {
		List<HistoryEntity> all = historyRepository.findAll();
		List<ProvideHistoryInfo> histories = new ArrayList<>();
		for (HistoryEntity now : all) {
			histories.add(
					ProvideHistoryInfo.builder()
							.history(now.getHistory())
							.createDate(now.getCreateDate())
							.build());
		}
		return HistoryResponse.builder().histories(histories).build();
	}
}
