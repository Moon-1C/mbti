package com.example.demo.web.dto.request;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class InvitePasswordRequest {

	private String password;
}
