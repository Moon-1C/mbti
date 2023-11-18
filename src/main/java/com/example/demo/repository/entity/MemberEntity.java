package com.example.demo.repository.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private long id;

	@Column(unique = true)
	private String email;

	private String name;

	private String password;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_survey_id")
	private MemberSurveyEntity memberSurvey;
}
