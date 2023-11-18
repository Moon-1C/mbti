package com.example.demo.web.dto.response;

import com.example.demo.domain.dto.response.ProvideHistoryInfo;
import java.util.List;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class HistoryResponse {

	private List<ProvideHistoryInfo> histories;
}
