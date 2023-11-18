package com.example.demo.repository.entity;

import javax.persistence.*;
import lombok.*;

@Entity(name = "chat_room_survey_question_entity")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChatRoomSurveyQuestionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_room_survey_question_id")
	private Long id;

	private double version;

	private String question;

	private String selection;
}
