package com.example.demo.repository.entity;

import javax.persistence.*;
import lombok.*;

@Entity(name = "spot_entity")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SpotEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spot_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room_id")
	private ChatRoomEntity chatRoom;

	private String spot;

	public void changeSpot(String newSpotName) {
		this.spot = newSpotName;
	}
}
