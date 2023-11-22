package com.example.demo.web.controller.v1;

import com.example.demo.domain.dto.request.VoteDestinationDomainRequest;
import com.example.demo.domain.usecase.chatroom.VoteDestinationService;
import com.example.demo.security.authentication.token.TokenUserDetailsService;
import com.example.demo.web.dto.request.VoteDestinationRequest;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
public class ChatRoomSpotUpdateController {

	private final VoteDestinationService voteDestinationService;
	private final TokenUserDetailsService tokenUserDetailsService;

	@PostMapping("{chatRoomId}/agree")
	public void spotUpdate(
			@PathVariable Long chatRoomId,
			@RequestBody VoteDestinationRequest request,
			HttpServletRequest httpServletRequest) {
		Long memberId = findMemberByToken(httpServletRequest);
		voteDestinationService.execute(
				VoteDestinationDomainRequest.builder()
						.chatRoomId(chatRoomId)
						.memberId(memberId)
						.voteResult(request.getVoteResult())
						.build());
	}

	private Long findMemberByToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String substring = authorization.substring(7, authorization.length());
		UserDetails userDetails = tokenUserDetailsService.loadUserByUsername(substring);
		Long memberId = Long.parseLong(userDetails.getUsername());
		return memberId;
	}
}
