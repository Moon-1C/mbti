package com.example.demo.repository.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Entity(name = "history_entity")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class HistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private Long id;

	private String history;

	@Column(name = "create_date")
	private Date createDate;
}
