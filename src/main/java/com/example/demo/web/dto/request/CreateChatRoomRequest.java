package com.example.demo.web.dto.request;

import java.sql.Date;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateChatRoomRequest {

	private String title;
	private String spot;
	private int headcount;
	private Date start;
	private Date end;
	private String password;
}
