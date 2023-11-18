package com.example.demo.domain.dto.response;

import java.util.List;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProvideQuestionInfo {

	private List<String> questions;

	private List<String> answers;
}
