package com.example.demo.repository.entity;

import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity(name = "member_entity")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(unique = true)
	private String email;

	private String name;

	private String password;

	@OneToMany(mappedBy = "member")
	private List<ChatRoomListEntity> chatRoomLists;

	public boolean isPassword(String target) {
		return password.equals(target);
	}

	@Builder.Default
	@Column(nullable = false)
	private Boolean deleted = false;

	public void delete() {
		this.deleted = true;
	}
}
