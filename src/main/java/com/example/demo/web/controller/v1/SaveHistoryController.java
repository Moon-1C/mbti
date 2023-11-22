package com.example.demo.web.controller.v1;

import com.example.demo.domain.usecase.chatroom.SaveHistoryService;
import com.example.demo.security.authentication.token.TokenUserDetailsService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
public class SaveHistoryController {

	private final SaveHistoryService saveHistoryService;
	private final TokenUserDetailsService tokenUserDetailsService;

	@PostMapping("/{chatroomId}/finish")
	public String save(@PathVariable Long chatroomId, HttpServletRequest request) {
		Long memberId = findMemberByToken(request);
		saveHistoryService.execute(chatroomId, memberId);
		return "redirect:/";
	}

	private Long findMemberByToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String substring = authorization.substring(7, authorization.length());
		UserDetails userDetails = tokenUserDetailsService.loadUserByUsername(substring);
		Long memberId = Long.parseLong(userDetails.getUsername());
		return memberId;
	}
}
