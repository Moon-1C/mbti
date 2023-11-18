package com.example.demo.domain.usecase.chatroom;

import com.example.demo.client.firebase.FirebaseClient;
import com.example.demo.client.gpt.AiResponse;
import com.example.demo.client.gpt.GptAiResponse;
import com.example.demo.domain.dto.request.VoteDestinationDomainRequest;
import com.example.demo.domain.model.VotePaper;
import com.example.demo.repository.entity.ChatRoomListEntity;
import com.example.demo.repository.repository.ChatRoomListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteDestinationService {

    private final double SELECT_RATIO = 0.66;

    private static final Map<Long, VotePaper> AllBallotPapers = new ConcurrentHashMap<>();  //chatRoomId, 투표용지
    private final ChatRoomListRepository chatRoomListRepository;

    public void execute(VoteDestinationDomainRequest request) {
        VotePaper chatRoomBallotPaper = AllBallotPapers.getOrDefault(request.getChatRoomId(),
                VotePaper.builder().paper(new ConcurrentHashMap<>()).build());

        chatRoomBallotPaper.put(request.getMemberId(), request.getVoteResult());
        List<ChatRoomListEntity> allByChatRoomId = chatRoomListRepository.findAllByChatRoomId(request.getChatRoomId());

        // 모두가 투표완료인 경우
        if (chatRoomBallotPaper.getSize() == allByChatRoomId.size()) {
            List<Integer> shouldModifiedSpot = compareVote(chatRoomBallotPaper, allByChatRoomId.size());
            //todo
            // 문항번호를 다시 여행지로 변환해줘야함
            List<String> shouldModifiedSpotName = new ArrayList<>();
            
            //gpt를 통해 수정된 내용을 받아오고
            AiResponse aiResponse = new GptAiResponse();
            String gptResponse = aiResponse.getResponse(String.join(",", shouldModifiedSpotName));

            //FirebaseClient로 전송
            FirebaseClient firebaseClient = new FirebaseClient();
            firebaseClient.postRequest(request.getChatRoomId(), gptResponse);


            chatRoomBallotPaper.reset();
        }


    }

    private List<Integer> compareVote(VotePaper chatRoomBallotPaper, int memberCount) {
        List<Integer> shouldModifiedSpot = new ArrayList<>();
        HashMap<Integer, Integer> paperCount = chatRoomBallotPaper.getPaperCount();
        int SelectSpotLimit = (int) Math.floor(memberCount * SELECT_RATIO);

        for (Integer spotId : paperCount.keySet()) {
            if (paperCount.get(spotId) < SelectSpotLimit) {
                shouldModifiedSpot.add(spotId);
            }
        }
        return shouldModifiedSpot;
    }
}
