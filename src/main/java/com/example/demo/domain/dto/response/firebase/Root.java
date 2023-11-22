package com.example.demo.domain.dto.response.firebase;

import java.util.List;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Root {

	private List<Document> documents;
}
