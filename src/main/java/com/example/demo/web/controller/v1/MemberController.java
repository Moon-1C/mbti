package com.example.demo.web.controller.v1;

import com.example.demo.domain.dto.response.SavedUserInfo;
import com.example.demo.domain.usecase.user.LoadUserService;
import com.example.demo.domain.usecase.user.SaveUserService;
import com.example.demo.support.ApiResponse;
import com.example.demo.support.ApiResponseGenerator;
import com.example.demo.web.dto.request.LoginUserRequest;
import com.example.demo.web.dto.request.SaveUserRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final SaveUserService saveUserService;
	private final LoadUserService loadUserService;

	@PostMapping("/login")
	public ApiResponse<ApiResponse.SuccessBody<SavedUserInfo>> login(
			@Valid @RequestBody LoginUserRequest request) {
		SavedUserInfo res = loadUserService.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.CREATED);
	}

	@PostMapping("/join")
	public ApiResponse<ApiResponse.SuccessBody<SavedUserInfo>> join(
			@Valid @RequestBody SaveUserRequest request) {
		SavedUserInfo res = saveUserService.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.CREATED);
	}
}
