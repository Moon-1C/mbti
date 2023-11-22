package com.example.demo.domain.dto.request;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NaverSummaryRequest {

	private Document document = new Document();
	private Option option = new Option();

	public void setMessage(String message) {
		this.document.content = message;
	}

	@Getter
	@ToString
	private class Document {
		private String content;
	}

	@Getter
	@ToString
	private class Option {
		private String language = "ko";
		private Integer tone = 3;
	}
}
