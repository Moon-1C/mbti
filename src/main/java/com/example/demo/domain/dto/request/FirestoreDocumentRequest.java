package com.example.demo.domain.dto.request;

import java.time.Instant;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FirestoreDocumentRequest {

	private String message;
	private Number createdAt;
	private String username;
	private String userId;
	private String time;
	private String type;
	private Long chatRoomId;

	public FirestoreDocumentRequest(Builder builder) {
		this.message = builder.message;
		this.createdAt = builder.createdAt;
		this.username = builder.username;
		this.userId = builder.userId;
		this.time = builder.time;
		this.type = builder.type;
		this.chatRoomId = builder.chatRoomId;
	}

	@NoArgsConstructor
	public static class Builder {
		private String message;
		private String username;
		private String userId;
		private String type;
		private Long chatRoomId;
		private Number createdAt = System.currentTimeMillis();
		private String time = Instant.now().toString();

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder chatRoomId(Long chatRoomId) {
			this.chatRoomId = chatRoomId;
			return this;
		}

		public FirestoreDocumentRequest build() {
			return new FirestoreDocumentRequest(this);
		}
	}
}
