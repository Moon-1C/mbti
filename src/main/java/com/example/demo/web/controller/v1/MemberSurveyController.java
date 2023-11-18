package com.example.demo.web.controller.v1;

import com.example.demo.domain.dto.response.ProvideQuestionInfo;
import com.example.demo.domain.usecase.user.ProvideMemberSurveyService;
import com.example.demo.domain.usecase.user.ReceiveMemberSurveyService;
import com.example.demo.security.authentication.token.TokenUserDetailsService;
import com.example.demo.support.ApiResponse;
import com.example.demo.support.ApiResponseGenerator;
import com.example.demo.web.dto.request.ReceiveMemberSurveyRequest;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/members/survey")
@RequiredArgsConstructor
public class MemberSurveyController {

	private final ProvideMemberSurveyService provideMemberSurveyService;
	private final TokenUserDetailsService tokenUserDetailsService;
	private final ReceiveMemberSurveyService receiveMemberSurveyService;

	@GetMapping("/{version}")
	public ApiResponse<ApiResponse.SuccessBody<ProvideQuestionInfo>> provide(
			@PathVariable double version) {
		ProvideQuestionInfo questions = provideMemberSurveyService.execute(version);

		return ApiResponseGenerator.success(questions, HttpStatus.OK);
	}

	@PostMapping
	public String receive(ReceiveMemberSurveyRequest survey, HttpServletRequest request) {
		Long memberId = findMemberByToken(request);
		receiveMemberSurveyService.execute(survey, memberId);

		return "redirect:/";
	}

	private Long findMemberByToken(HttpServletRequest request) {
		String authentication = request.getHeader("Authentication");
		String substring = authentication.substring(7, authentication.length());
		UserDetails userDetails = tokenUserDetailsService.loadUserByUsername(substring);
		Long memberId = Long.parseLong(userDetails.getUsername());
		return memberId;
	}
}
