package com.example.demo.web.dto.response;

import com.example.demo.repository.entity.constant.ChatRoomStatus;
import java.util.Map;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChatRoomResponse {

	private Map<Long, ChatRoomStatus> chatRooms;
}
