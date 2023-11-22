package com.example.demo.web.dto.response;

import java.util.List;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SpotResponse {

	private List<String> spots;
	private Long managerId;
	private boolean isCompleted;
	private Long currentMemberId;
}
