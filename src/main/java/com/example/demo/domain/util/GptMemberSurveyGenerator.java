package com.example.demo.domain.util;

import com.example.demo.domain.dto.response.SummaryMemberSurveyResponse;
import com.example.demo.repository.entity.ChatRoomListEntity;
import com.example.demo.repository.entity.MemberSurveyQuestionEntity;
import com.example.demo.repository.repository.ChatRoomListRepository;
import com.example.demo.repository.repository.MemberSurveyQuestionRepository;
import com.example.demo.repository.repository.MemberSurveyRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GptMemberSurveyGenerator {

	private final ChatRoomListRepository chatRoomListRepository;
	private final MemberSurveyRepository memberSurveyRepository;
	private final MemberSurveyQuestionRepository memberSurveyQuestionRepository;

	public SummaryMemberSurveyResponse execute(Long chatRoomId, double version) {
		List<ChatRoomListEntity> allChatList = chatRoomListRepository.findAllByChatRoomId(chatRoomId);

		int totalMembers = allChatList.size();
		int[] sumScore = new int[getSurveySize(version)];
		Arrays.fill(sumScore, 0);

		// 항목별로 설문에 참여한 멤버들의 점수를 합친다.
		for (ChatRoomListEntity chatRoomListEntity : allChatList) {
			String answers =
					memberSurveyRepository
							.findByMemberIdAndVersion(chatRoomListEntity.getMember().getId(), version)
							.orElseThrow(() -> new IllegalArgumentException("Error!"))
							.getAnswer();

			int[] scores =
					Arrays.stream(answers.split(",")).mapToInt(score -> Integer.parseInt(score)).toArray();
			for (int i = 0; i < scores.length; i++) {
				sumScore[i] += scores[i];
			}
		}

		double[] averageScore = getAverageScore(version, sumScore, totalMembers);

		// 질문지 가져와서 응답 객체를 생성
		List<MemberSurveyQuestionEntity> surveys =
				memberSurveyQuestionRepository.findAllByVersion(version);
		return generateResponse(surveys, averageScore);
	}

	private double[] getAverageScore(double version, int[] sumScore, int totalMembers) {
		double[] averageScore = new double[getSurveySize(version)];
		for (int i = 0; i < averageScore.length; i++) {
			averageScore[i] = (double) sumScore[i] / totalMembers;
		}
		return averageScore;
	}

	private int getSurveySize(double version) {
		return Long.valueOf(memberSurveyQuestionRepository.findAllByVersion(version).stream().count())
				.intValue();
	}

	private SummaryMemberSurveyResponse generateResponse(
			List<MemberSurveyQuestionEntity> surveys, double[] averageScore) {
		List<String> data = new ArrayList<>();
		for (int i = 0; i < surveys.size(); i++) {
			data.add(format(surveys.get(i).getQuestion(), averageScore[i]));
		}

		return SummaryMemberSurveyResponse.builder().surveys(data).build();
	}

	private String format(String question, double score) {
		return question + " : " + score + "점";
	}
}
