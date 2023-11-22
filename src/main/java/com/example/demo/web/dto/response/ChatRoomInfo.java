package com.example.demo.web.dto.response;

import com.example.demo.repository.entity.constant.ChatRoomStatus;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChatRoomInfo {

	private Long chatroomId;
	private String title;
	private ChatRoomStatus chatroomStatus;
}
