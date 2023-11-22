package com.example.demo.repository.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.*;
import lombok.*;

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

	private String title;

	private String password;

	private String spot;

	private Integer headcount;

	private Date start;

	private Date end;

	@OneToOne
	@JoinColumn(name = "member_id")
	private MemberEntity manager;

	private Boolean isComplete;

	@OneToMany(mappedBy = "chatRoom")
	private List<ChatRoomListEntity> chatRoomLists;

	public long getTripPeriod() {
		LocalDate startDate = start.toLocalDate();
		LocalDate endDate = end.toLocalDate();

		return ChronoUnit.DAYS.between(startDate, endDate);
	}

	public void changeComplete() {
		this.isComplete = true;
	}
}
