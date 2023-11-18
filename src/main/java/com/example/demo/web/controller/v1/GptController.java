package com.example.demo.web.controller.v1;

import com.example.demo.client.gpt.AiResponse;
import com.example.demo.client.gpt.GptAiResponse;
import com.example.demo.support.ApiResponse;
import com.example.demo.support.ApiResponseGenerator;
import com.example.demo.web.dto.request.GptMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/gpt")
@RequiredArgsConstructor
public class GptController {

	AiResponse aiResponse = new GptAiResponse();

	@PostMapping
	public ApiResponse<ApiResponse.SuccessBody<String>> execute(
			@RequestBody GptMessageRequest request) {

		return ApiResponseGenerator.success(aiResponse.getResponse(request.getData()), HttpStatus.OK);
	}
}
