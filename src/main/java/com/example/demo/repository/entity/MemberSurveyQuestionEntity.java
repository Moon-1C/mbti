package com.example.demo.repository.entity;

import javax.persistence.*;
import lombok.*;

@Entity(name = "member_survey_question_entity")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MemberSurveyQuestionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_survey_question_id")
	private Long id;

	private double version;

	private String question;
}
