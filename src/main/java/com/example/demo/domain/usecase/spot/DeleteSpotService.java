package com.example.demo.domain.usecase.spot;

import com.example.demo.repository.entity.SpotEntity;
import com.example.demo.repository.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteSpotService {

	private SpotRepository spotRepository;

	@Transactional
	public void execute(Long spotId) {
		SpotEntity spot =
				spotRepository.findById(spotId).orElseThrow(() -> new IllegalArgumentException("Error"));
		spotRepository.delete(spot);
	}
}
