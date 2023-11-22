package com.example.demo.domain.dto.response.firebase;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Document {
	private String name;
	private Field fields;
	private String createTime;
	private String updateTime;
}
