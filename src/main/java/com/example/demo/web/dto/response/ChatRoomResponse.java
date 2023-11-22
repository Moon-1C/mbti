package com.example.demo.web.dto.response;

import java.util.List;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChatRoomResponse {

	private List<ChatRoomInfo> chatRoomInfos;
}
