package com.example.demo.domain.dto.response;

import java.util.Date;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProvideHistoryInfo {

	private String history;
	private Date createDate;
}
