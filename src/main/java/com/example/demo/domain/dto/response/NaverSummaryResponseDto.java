package com.example.demo.domain.dto.response;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NaverSummaryResponseDto {

	private String summary;
}
