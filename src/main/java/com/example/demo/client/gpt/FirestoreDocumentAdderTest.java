package com.example.demo.client.gpt;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.domain.dto.request.FirestoreDocumentRequest;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class FirestoreDocumentAdderTest {

	FirestoreDocumentAdder adder = new FirestoreDocumentAdder();

	@Test
	void run() throws IOException {
		FirestoreDocumentRequest request =
				new FirestoreDocumentRequest.Builder()
						.userId("test_user_id")
						.username("test_user")
						.chatRoomId(1L)
						.message("message for test")
						.type("type")
						.build();
		adder.send(request);
	}
}
