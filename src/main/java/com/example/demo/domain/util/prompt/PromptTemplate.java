package com.example.demo.domain.util.prompt;

public class PromptTemplate {

	private static final String START_PROMPT_MESSAGE =
			"너는 제주도 여행코스를 추천해주는 AI야. "
					+ "선호하는 여행성향 정보를 가지고 여행지를 추천해줘야되고"
					+ "사용자들의 설문점수로 1(매우 선호하지 않음) ~ 5(매우 선호함)의 정보를 이용해서도 추천해 줘야 해"
					+ "여행지를 추천해줄때 너는 여행지들을 콤마(,)로 구분해서 여행지이름만 출력해야해. {";
	private static final String END_PROMPT_MESSAGE_1 = "} 이런 정보를 가지고 여행지를 ";
	private static final String END_PROMPT_MESSAGE_2 = "개 추천해줘 ";
	private static final String END_PROMPT_MESSAGE_3 = "개 새로 추천해줘 ";

	private static final String RE_START_PROMPT_MESSAGE =
			"너는 제주도 여행코스를 추천해주는 AI야. "
					+ "채팅 대화 내용과 이전에 마음에 들지 않았던 여행지 정보를 가지고 마음에 들만한 여행지를 추천해 줘야 해"
					+ "여행지를 추천해줄때 너는 여행지들을 콤마(,)로 구분해서 여행지이름만 출력해야해. { 채팅 대화 내용 : ";
	private static final String RE_END_PROMPT_MESSAGE_0 = ", 이전에 마음에 들지 않았던 여행지 : ";
	private static final String RE_END_PROMPT_MESSAGE_1 = "} 이런 정보를 가지고 여행지를 무조건 ";
	private static final String RE_END_PROMPT_MESSAGE_2 = "개 추천해줘 ";
	private static final String RE_END_PROMPT_MESSAGE_3 = "개 새로 추천해줘 ";

	private static final String AFTER_CHANGE_MESSAGE_1 = "수정된 여행지는 다음과 같습니다.\n {";
	private static final String AFTER_CHANGE_MESSAGE_2 = " }\n 갱신을 위해 왼쪽 위의 새로고침을 눌러주세요";

	public static String recommendSpot(String message, int count) {

		return START_PROMPT_MESSAGE + message + END_PROMPT_MESSAGE_1 + count + END_PROMPT_MESSAGE_2;
	}

	public static String reRecommendSpot(String chatMessage, String distasteSpot, int count) {

		return RE_START_PROMPT_MESSAGE
				+ chatMessage
				+ RE_END_PROMPT_MESSAGE_0
				+ distasteSpot
				+ RE_END_PROMPT_MESSAGE_1
				+ count
				+ RE_END_PROMPT_MESSAGE_3;
	}

	public static String messageAfterGptChange(String spots) {
		return AFTER_CHANGE_MESSAGE_1 + spots + AFTER_CHANGE_MESSAGE_2;
	}
}
