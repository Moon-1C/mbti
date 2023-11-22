package com.example.demo.domain.dto.response.firebase;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Field {
	private Object chatRoomId;
	private Object createdAt;
	private Object userId;
	private Object time;
	private Object username;
	private Object message;
	private Object type;
}
