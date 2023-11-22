package com.example.demo.client.firebase;

import com.example.demo.domain.dto.response.firebase.Document;
import com.example.demo.domain.dto.response.firebase.Root;
import com.google.gson.Gson;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FirebaseClient {

	private final String URL =
			"https://firestore.googleapis.com/v1/projects/nwitter-reloaded-683ef/databases/(default)/documents/messages"; // todo

	private final Gson gson = new Gson();

	public void postRequest(Long chatRoomId, String message) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(URL + chatRoomId, message, String.class);
	}

	public String getResponse() {

		String response = connect();
		Gson gson = new Gson();
		Root root = gson.fromJson(response, Root.class);
		List<Document> documents = root.getDocuments();
		String chat = "";
		for (Document now : documents) {
			chat +=
					getRealString(now.getFields().getUsername().toString())
							+ " : "
							+ getRealString(now.getFields().getMessage().toString())
							+ "\n";
		}
		return chat;
	}

	private String connect() {

		// RestTemplate 생성
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(URL, String.class);
		return result.getBody();
	}

	private String getRealString(String data) {
		return data.substring(13, data.length() - 1);
	}
}
