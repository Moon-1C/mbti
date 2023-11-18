package com.example.demo.domain.usecase.user;

import com.example.demo.repository.entity.MemberEntity;
import com.example.demo.repository.entity.MemberSurveyEntity;
import com.example.demo.repository.repository.MemberRepository;
import com.example.demo.repository.repository.MemberSurveyRepository;
import com.example.demo.web.dto.request.ReceiveMemberSurveyRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReceiveMemberSurveyService {

	private final MemberSurveyRepository memberSurveyRepository;
	private final MemberRepository memberRepository;

	public void execute(ReceiveMemberSurveyRequest request, Long id) {
		Optional<MemberEntity> findMember = memberRepository.findById(id);
		if (findMember.isPresent()) {
			memberSurveyRepository.save(
					MemberSurveyEntity.builder()
							.member(findMember.get())
							.version(request.getSurveyVersion())
							.answer(request.getSurveyResult())
							.build());
		}
	}
}
