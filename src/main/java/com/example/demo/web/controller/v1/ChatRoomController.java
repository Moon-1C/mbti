package com.example.demo.web.controller.v1;

import com.example.demo.domain.usecase.chatroom.*;
import com.example.demo.security.authentication.token.TokenUserDetailsService;
import com.example.demo.support.ApiResponse;
import com.example.demo.support.ApiResponseGenerator;
import com.example.demo.web.dto.request.CreateChatRoomRequest;
import com.example.demo.web.dto.request.CreateChatSurveyResultRequest;
import com.example.demo.web.dto.response.ChatRoomResponse;
import com.example.demo.web.dto.response.ChatSurveyQuestionResponse;
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

	private final TokenUserDetailsService tokenUserDetailsService;

	@PostMapping
	public String save(
			@RequestBody @Valid CreateChatRoomRequest requestData, HttpServletRequest request) {
		Long memberId = findMemberByToken(request);
		Long chatroomId = createChatRoomService.execute(requestData);

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
	public String submitSurvey(CreateChatSurveyResultRequest request) {
		createChatSurveyResultService.save(request);
		return "redirect:/api/v1/chatrooms";
	}

	private Long findMemberByToken(HttpServletRequest request) {
		String authentication = request.getHeader("Authentication");
		String substring = authentication.substring(7, authentication.length());
		UserDetails userDetails = tokenUserDetailsService.loadUserByUsername(substring);
		Long memberId = Long.parseLong(userDetails.getUsername());
		return memberId;
	}
}
