package com.example.demo.repository.entity;

import com.example.demo.repository.entity.constant.ChatRoomStatus;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "chat_room_entity")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChatRoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_room_id")
	private Long id;

	@OneToMany
	@JoinColumn(name = "chat_survey_id")
	private List<ChatSurveyEntity> chatSurvey;

	private String title;

	private String password;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@ColumnDefault("'SURVEY'")
	private ChatRoomStatus status = ChatRoomStatus.SURVEY;

	private String spot;

	private Integer headcount;

	private Date start;

	private Date end;

	@OneToMany(mappedBy = "chatRoom")
	private List<ChatRoomListEntity> chatRoomLists;
}
