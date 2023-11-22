package com.example.demo.domain.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VotePaper {

	private LinkedHashMap<Long, String> paper; // memberId, 결과

	public void put(Long memberId, String result) {
		paper.put(memberId, result);
	}

	public int getSize() {
		return paper.size();
	}

	public HashMap<Integer, Integer> getPaperCount() { // 문항번호 , 나온 문항의 true 개수
		LinkedHashMap<Integer, Integer> paperCount = new LinkedHashMap<>();
		for (Long memberId : paper.keySet()) {
			String memberVote = paper.get(memberId);
			String[] voteSheet = memberVote.split(",");
			for (int i = 0; i < voteSheet.length; i++) {
				paperCount.put(i, 0);
			}
			break;
		}

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
				paperCount.put(i, paperCount.get(i) + 1);
			}
		}
	}
}
