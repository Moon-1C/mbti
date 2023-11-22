package com.example.demo.domain.usecase.user;

import com.example.demo.domain.dto.response.SavedUserInfo;
import com.example.demo.repository.entity.MemberEntity;
import com.example.demo.repository.repository.MemberRepository;
import com.example.demo.security.authentication.authority.Roles;
import com.example.demo.support.token.TokenGenerator;
import com.example.demo.web.dto.request.LoginUserRequest;
import com.example.demo.web.dto.request.SaveUserRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoadUserService {

	private final MemberRepository memberRepository;

	private final TokenGenerator tokenGenerator;

	private static final Long NOT_EXIST_MEMBER = -1L;

	@Transactional
	public SavedUserInfo execute(LoginUserRequest request) {

		Long existMember = isExistMember(request.getEmail());

		if (Objects.equals(existMember, NOT_EXIST_MEMBER)) {
			throw new IllegalArgumentException("없는  멤버");
		}

		MemberEntity member = getMember(existMember);

		validatePassword(member, request.getPassword());

		return getExistedMember(member.getId());
	}

	private Long isExistMember(String email) {
		Optional<MemberEntity> source = memberRepository.findByEmailAndDeletedFalse(email);

		if (source.isPresent()) {
			return source.get().getId();
		}

		return NOT_EXIST_MEMBER;
	}

	private MemberEntity getMember(Long existMember) {
		return (MemberEntity)
				Objects.requireNonNull(memberRepository.findByIdAndDeletedFalse(existMember).orElse(null));
	}

	private void validatePassword(MemberEntity member, String requestPassword) {
		if (!member.isPassword(requestPassword)) {
			throw new IllegalArgumentException(); // todo
		}
	}

	private Long saveMember(SaveUserRequest request) {
		return memberRepository
				.save(
						MemberEntity.builder()
								.name(request.getName())
								.email(request.getEmail())
								.password(request.getPassword())
								.build())
				.getId();
	}

	private SavedUserInfo getExistedMember(Long memberId) {
		return SavedUserInfo.builder()
				.id(memberId)
				.token(tokenGenerator.generateAuthToken(memberId, List.of(Roles.USER)))
				.isRegistered(false)
				.build();
	}

	private SavedUserInfo getNewMember(Long memberId) {
		return SavedUserInfo.builder()
				.id(memberId)
				.token(tokenGenerator.generateAuthToken(memberId, List.of(Roles.USER)))
				.isRegistered(true)
				.build();
	}
}
