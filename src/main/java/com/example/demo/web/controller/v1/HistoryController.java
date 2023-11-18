package com.example.demo.web.controller.v1;

import com.example.demo.domain.usecase.history.ProvideHistoryService;
import com.example.demo.support.ApiResponse;
import com.example.demo.support.ApiResponseGenerator;
import com.example.demo.web.dto.response.HistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

	private final ProvideHistoryService provideHistoryService;

	@GetMapping
	public ApiResponse<ApiResponse.SuccessBody<HistoryResponse>> provide() {
		HistoryResponse histories = provideHistoryService.execute();

		return ApiResponseGenerator.success(histories, HttpStatus.OK);
	}
}
