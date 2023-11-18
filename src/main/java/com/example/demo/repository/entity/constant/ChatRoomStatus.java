package com.example.demo.repository.entity.constant;

public enum ChatRoomStatus {
	COMPLETE("참여 하기"),
	WAITING("대기 중"),
	SURVEY("성향 조사");

	private String status;

	ChatRoomStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}
