package com.example.demo.client.firebase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FirebaseClientTest {

	@Test
	void FirebaseTest() {
		FirebaseClient firebaseClient = new FirebaseClient();
		String response = firebaseClient.getResponse();
		System.out.println(response);
	}
}
