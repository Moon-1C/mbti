package com.example.demo.domain.usecase.user;

import com.example.demo.domain.dto.response.SavedUserInfo;
import com.example.demo.repository.entity.MemberEntity;
import com.example.demo.repository.repository.MemberRepository;
import com.example.demo.security.authentication.authority.Roles;
import com.example.demo.support.token.TokenGenerator;
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
public class SaveUserService {

	private final MemberRepository memberRepository;

	private final TokenGenerator tokenGenerator;

	private static final Long NOT_EXIST_MEMBER = -1L;

	@Transactional
	public SavedUserInfo execute(SaveUserRequest request) {

		Long existMember = isExistMember(request.getName());

		if (!Objects.equals(existMember, NOT_EXIST_MEMBER)) {
			MemberEntity member = getMember(existMember);

			validatePassword(member, request.getPassword());

			return getExistedMember(member.getId());
		}

		Long savedMemberId = saveMember(request);

		return getNewMember(savedMemberId);
	}

	private Long isExistMember(String name) {
		Optional<MemberEntity> source = memberRepository.findByNameAndDeletedFalse(name);

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
