package com.example.demo.domain.model;

import lombok.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VotePaper {

    private ConcurrentHashMap<Long, String> paper;   //memberId, 결과

    public void put(Long memberId, String result) {
        paper.put(memberId, result);
    }

    public int getSize() {
        return paper.size();
    }

    public HashMap<Integer, Integer> getPaperCount() {    //문항번호 , 나온 문항의 true 개수
        HashMap<Integer, Integer> paperCount = new HashMap<>();
        for (Long memberId : paper.keySet()) {
            String memberVote = paper.get(memberId);
            String[] voteSheet = memberVote.split(",");
            countVote(paperCount, voteSheet);
        }

        return paperCount;

    }

    public void reset() {
        paper.clear();
    }

    private void countVote(HashMap<Integer, Integer> paperCount, String[] voteSheet) {
        for (int i = 0; i < voteSheet.length; i++) {
            if (voteSheet[i].equals("true")) {
                paperCount.put(i, paperCount.getOrDefault(i, 0) + 1);
            }
        }
    }


}
