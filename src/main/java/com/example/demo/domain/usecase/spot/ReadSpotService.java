package com.example.demo.domain.usecase.spot;

import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.repository.ChatRoomRepository;
import com.example.demo.repository.repository.SpotRepository;
import com.example.demo.web.dto.response.SpotResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadSpotService {

	private final SpotRepository spotRepository;
	private final ChatRoomRepository chatRoomRepository;

	@Transactional(readOnly = true)
	public SpotResponse execute(Long chatroomId, Long memberId) {
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatroomId).get();

		List<String> spots = new ArrayList<>();
		spotRepository.findAllByChatRoomId(chatroomId).forEach(spot -> spots.add(spot.getSpot()));

		return SpotResponse.builder()
				.spots(spots)
				.managerId(chatRoomEntity.getManager().getId())
				.isCompleted(chatRoomEntity.getIsComplete())
				.currentMemberId(memberId)
				.build();
	}
}
