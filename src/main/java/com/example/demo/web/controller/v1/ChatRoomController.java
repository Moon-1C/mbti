package com.example.demo.web.controller.v1;

import com.example.demo.client.gpt.AiResponse;
import com.example.demo.client.gpt.GptAiResponse;
import com.example.demo.domain.usecase.chatroom.*;
import com.example.demo.domain.usecase.spot.CreateSpotService;
import com.example.demo.domain.usecase.spot.ReadSpotService;
import com.example.demo.domain.util.GptChatSurveyGenerator;
import com.example.demo.domain.util.prompt.PromptTemplate;
import com.example.demo.repository.entity.constant.ChatRoomStatus;
import com.example.demo.security.authentication.token.TokenUserDetailsService;
import com.example.demo.support.ApiResponse;
import com.example.demo.support.ApiResponseGenerator;
import com.example.demo.web.dto.request.CreateChatRoomRequest;
import com.example.demo.web.dto.request.CreateChatSurveyResultRequest;
import com.example.demo.web.dto.response.ChatRoomResponse;
import com.example.demo.web.dto.response.ChatSurveyQuestionResponse;
import com.example.demo.web.dto.response.SpotResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
public class ChatRoomController {

	private final CreateChatRoomService createChatRoomService;
	private final ReadChatRoomService readChatRoomService;
	private final ReadChatSurveyQuestionService readChatSurveyQuestionService;
	private final CreateChatRoomListService createChatRoomListService;
	private final CreateChatSurveyResultService createChatSurveyResultService;
	private final UpdateChatRoomStatusService updateChatRoomStatusService;
	private final ReadChatRoomTitleService readChatRoomTitleService;
	private final ReadSpotService readSpotService;
	private final CreateSpotService createSpotService;
	private final GptChatSurveyGenerator gptChatSurveyGenerator;
	private final AiResponse aiResponse = new GptAiResponse();

	private final TokenUserDetailsService tokenUserDetailsService;

	private static final int GPT_RECOMMEND_COUNT = 5;

	@PostMapping
	public String save(
			@RequestBody @Valid CreateChatRoomRequest requestData, HttpServletRequest request) {
		Long memberId = findMemberByToken(request);
		Long chatroomId = createChatRoomService.execute(requestData, memberId);

		createChatRoomListService.create(memberId, chatroomId);
		return "redirect:/api/v1/chatrooms";
	}

	@GetMapping
	public ApiResponse<ApiResponse.SuccessBody<ChatRoomResponse>> loadChatRooms(
			HttpServletRequest request) {
		Long memberId = findMemberByToken(request);
		ChatRoomResponse res = readChatRoomService.execute(memberId);
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/survey/{version}")
	public ApiResponse<ApiResponse.SuccessBody<ChatSurveyQuestionResponse>> loadSurvey(
			@PathVariable double version) {
		ChatSurveyQuestionResponse res = readChatSurveyQuestionService.execute(version);
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@PostMapping("/survey")
	public String submitSurvey(
			@RequestBody CreateChatSurveyResultRequest requestData, HttpServletRequest request) {
		createChatSurveyResultService.execute(requestData);
		changeChatroomStatus(requestData, request);
		return "redirect:/api/v1/chatrooms";
	}

	@GetMapping("/{chatroomId}/title")
	public String getChatRoomTitle(@PathVariable Long chatroomId) {
		return readChatRoomTitleService.execute(chatroomId);
	}

	@GetMapping("/{chatroomId}/recommendation")
	public ApiResponse<ApiResponse.SuccessBody<SpotResponse>> getRecommendedSpot(
			@PathVariable Long chatroomId, HttpServletRequest request) {
		Long memberId = findMemberByToken(request);
		SpotResponse res = readSpotService.execute(chatroomId,memberId);
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	private void changeChatroomStatus(
			CreateChatSurveyResultRequest requestData, HttpServletRequest request) {
		Long chatRoomId = requestData.getChatRoomId();
		updateChatRoomStatusService.updateChatStatus(
				findMemberByToken(request), chatRoomId, ChatRoomStatus.WAITING);
		if (updateChatRoomStatusService.checkComplete(chatRoomId)) {
			updateChatRoomStatusService.updateAllComplete(chatRoomId);
			saveRecommendSpot(requestData, chatRoomId);
		}
	}

	private void saveRecommendSpot(CreateChatSurveyResultRequest requestData, Long chatRoomId) {
		String gptResult = getRecommendedSpotBasedSurveyResult(requestData, chatRoomId);
		String[] items = gptResult.split(",");
		for (String item : items) {
			createSpotService.execute(item, chatRoomId);
		}
	}

	private String getRecommendedSpotBasedSurveyResult(
			CreateChatSurveyResultRequest requestData, Long chatRoomId) {
		return gptRecommendSpot(
				gptChatSurveyGenerator
						.execute(chatRoomId, requestData.getVersion())
						.getSurveys()
						.toString());
	}

	private Long findMemberByToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String substring = authorization.substring(7, authorization.length());
		UserDetails userDetails = tokenUserDetailsService.loadUserByUsername(substring);
		Long memberId = Long.parseLong(userDetails.getUsername());
		return memberId;
	}

	private String gptRecommendSpot(String message) {
		String query = PromptTemplate.recommendSpot(message, GPT_RECOMMEND_COUNT);
		return aiResponse.getResponse(PromptTemplate.recommendSpot(message, GPT_RECOMMEND_COUNT));
	}
}
