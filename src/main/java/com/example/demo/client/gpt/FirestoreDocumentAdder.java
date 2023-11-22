package com.example.demo.client.gpt;

import com.example.demo.domain.dto.request.FirestoreDocumentRequest;
import com.google.gson.JsonObject;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public class FirestoreDocumentAdder {

	public void send(FirestoreDocumentRequest request) {
		String url =
				"https://firestore.googleapis.com/v1/projects/nwitter-reloaded-683ef/databases/(default)/documents/messages";

		// 문서 데이터 생성
		JsonObject documentData = createDocumentData(request);

		// Firestore에 문서 추가
		try {
			addDocumentToFirestore(url, documentData);
		} catch (IOException e) {
			throw new IllegalArgumentException("입력 오류");
		}
	}

	private JsonObject createDocumentData(FirestoreDocumentRequest request) {
		JsonObject fields = new JsonObject();

		JsonObject message = new JsonObject();
		message.addProperty("stringValue", request.getMessage());
		fields.add("message", message);

		JsonObject createdAt = new JsonObject();
		createdAt.addProperty("integerValue", request.getCreatedAt());
		fields.add("createdAt", createdAt);

		JsonObject username = new JsonObject();
		username.addProperty("stringValue", request.getUsername());
		fields.add("username", username);

		JsonObject userId = new JsonObject();
		userId.addProperty("stringValue", request.getUserId());
		fields.add("userId", userId);

		JsonObject time = new JsonObject();
		time.addProperty("timestampValue", request.getTime());
		fields.add("time", time);

		JsonObject type = new JsonObject();
		type.addProperty("stringValue", request.getType());
		fields.add("type", type);

		JsonObject chatRoomId = new JsonObject();
		chatRoomId.addProperty("integerValue", request.getChatRoomId());
		fields.add("chatRoomId", chatRoomId);

		JsonObject documentData = new JsonObject();
		documentData.add("fields", fields);

		return documentData;
	}

	private void addDocumentToFirestore(String url, JsonObject documentData) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-type", "application/json");

		StringEntity requestEntity = new StringEntity(documentData.toString());
		httpPost.setEntity(requestEntity);

		HttpResponse response = client.execute(httpPost);
		String responseString = EntityUtils.toString(response.getEntity());
		log.debug("send firebaseResult: {}", responseString);
		client.close();
	}
}
