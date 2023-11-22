package com.example.demo.domain.usecase.chatroom;

import static com.example.demo.domain.util.prompt.PromptTemplate.*;

import com.example.demo.client.firebase.FirebaseClient;
import com.example.demo.client.gpt.AiResponse;
import com.example.demo.client.gpt.FirestoreDocumentAdder;
import com.example.demo.client.gpt.GptAiResponse;
import com.example.demo.client.summary.NaverSummaryResponse;
import com.example.demo.client.summary.SummaryResponse;
import com.example.demo.domain.dto.request.FirestoreDocumentRequest;
import com.example.demo.domain.dto.request.VoteDestinationDomainRequest;
import com.example.demo.domain.model.VotePaper;
import com.example.demo.repository.entity.ChatRoomEntity;
import com.example.demo.repository.entity.ChatRoomListEntity;
import com.example.demo.repository.entity.SpotEntity;
import com.example.demo.repository.repository.ChatRoomListRepository;
import com.example.demo.repository.repository.ChatRoomRepository;
import com.example.demo.repository.repository.SpotRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteDestinationService {

	private final FirebaseClient firebaseClient;
	private final double SELECT_RATIO = 0.66;

	private static final Map<Long, VotePaper> AllBallotPapers =
			new LinkedHashMap<>(); // chatRoomId, 투표용지

	private final ChatRoomListRepository chatRoomListRepository;

	private final ChatRoomRepository chatRoomRepository;

	private final SpotRepository spotRepository;

	private final AiResponse ai = new GptAiResponse();
	private final SummaryResponse summaryResponse = new NaverSummaryResponse();

	@Transactional
	public void execute(VoteDestinationDomainRequest request) {
		if (!AllBallotPapers.containsKey(request.getChatRoomId())) {
			AllBallotPapers.put(
					request.getChatRoomId(), VotePaper.builder().paper(new LinkedHashMap<>()).build());
		}
		VotePaper votePaper = AllBallotPapers.get(request.getChatRoomId());

		votePaper.put(request.getMemberId(), request.getVoteResult());
		List<ChatRoomListEntity> allByChatRoomId =
				chatRoomListRepository.findAllByChatRoomId(request.getChatRoomId());

		// 모두가 투표완료인 경우
		if (votePaper.getSize() == allByChatRoomId.size()) {
			List<Integer> shouldModifiedSpot = compareVote(votePaper, allByChatRoomId.size());
			// todo
			// 문항번호를 다시 여행지로 변환해줘야함
			List<SpotEntity> allSpotByChatRoomId =
					spotRepository.findAllByChatRoomId(request.getChatRoomId());

			List<SpotEntity> shouldModifiedSpotEntites = new ArrayList<>();

			for (int i = 0; i < shouldModifiedSpot.size(); i++) {
				shouldModifiedSpotEntites.add(allSpotByChatRoomId.get(shouldModifiedSpot.get(i)));
			}
			List<String> shouldModifiedSpotName =
					shouldModifiedSpotEntites.stream()
							.map(e -> e.getSpot())
							.collect(Collectors.toCollection(ArrayList::new));

			if (shouldModifiedSpotName.size() == 0) {
				// chatRoom 상태 업데이트
				ChatRoomEntity chatRoom = chatRoomRepository.findById(request.getChatRoomId()).get();
				chatRoom.changeComplete();
				chatRoomRepository.save(chatRoom);

				votePaper.reset();
				return;
			}

			// firebase로 채팅 내용 불러오고 요약

			String chatMessage = firebaseClient.getResponse();

			String chatSummary = summaryResponse.getResponse(chatMessage);
			System.out.println(chatSummary);

			// gpt를 통해 수정된 내용을 받아오고 채팅내용과 함께 gpt로 전송  //todo

			String gptResponse =
					ai.getResponse(
							reRecommendSpot(
									chatMessage,
									String.join(",", shouldModifiedSpotName),
									shouldModifiedSpotName.size()));

			String[] splitGptResponse = gptResponse.split(",");
			for (int i = 0; i < splitGptResponse.length; i++) {
				SpotEntity spotEntity = shouldModifiedSpotEntites.get(i);
				spotEntity.changeSpot(splitGptResponse[i]);
				spotRepository.save(spotEntity);
			}

			String promptMessage = messageAfterGptChange(String.join(",", splitGptResponse));

			// FirebaseClient로 전송
			FirestoreDocumentAdder adder = new FirestoreDocumentAdder();
			FirestoreDocumentRequest firestoreDocumentRequest =
					new FirestoreDocumentRequest.Builder()
							.userId("test_user_id")
							.username("test_user")
							.chatRoomId(1L)
							.message("message for test")
							.type("type")
							.build();
			adder.send(firestoreDocumentRequest);

			// votePaper 리셋
			votePaper.reset();
		}
	}

	private void changeComplete(VoteDestinationDomainRequest request, VotePaper votePaper) {
		ChatRoomEntity chatRoom = chatRoomRepository.findById(request.getChatRoomId()).get();
		chatRoom.changeComplete();
		chatRoomRepository.save(chatRoom);

		votePaper.reset();
	}

	private List<Integer> compareVote(VotePaper chatRoomBallotPaper, int memberCount) {
		List<Integer> shouldModifiedSpot = new ArrayList<>();
		HashMap<Integer, Integer> paperCount = chatRoomBallotPaper.getPaperCount();
		int SelectSpotLimit = (int) Math.ceil(memberCount * SELECT_RATIO);

		for (Integer spotId : paperCount.keySet()) {
			if (paperCount.get(spotId) < SelectSpotLimit) {
				shouldModifiedSpot.add(spotId);
			}
		}
		return shouldModifiedSpot;
	}
}
