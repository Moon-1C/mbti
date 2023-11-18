package com.example.demo.domain.dto.response;

import com.example.demo.support.token.AuthToken;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SavedUserInfo {

	private Long id;
	private AuthToken token;
	private Boolean isRegistered;
}
