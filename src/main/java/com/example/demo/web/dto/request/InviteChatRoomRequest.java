package com.example.demo.web.dto.request;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class InviteChatRoomRequest {

	private Long chatRoomId;
	private Long memberId;
	private String password;
}
