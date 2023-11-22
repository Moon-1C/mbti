package com.example.demo.client.gpt;

import com.example.demo.web.dto.request.GptMessageRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class GptAiResponse implements AiResponse {

	@Override
	public String getResponse(String message) {

		String response = connect(message);

		return response;
	}

	private String connect(String message) {
		String url = "http://localhost:8000/predict";

		GptMessageRequest requestDto = GptMessageRequest.builder().data(message).build();

		// RestTemplate 생성
		RestTemplate restTemplate = new RestTemplate();

		// 요청 매개변수 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<GptMessageRequest> request = new HttpEntity<>(requestDto, headers);

		// HTTP 요청 및 응답 처리
		ResponseEntity<String> responseDto =
				restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		return responseDto.getBody();
	}
}
