package com.example.demo.client.firebase;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class FirebaseClient {

    private final String URL = "";  //todo

    public void postRequest(Long chatRoomId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(URL + chatRoomId, message, String.class);

    }

    public String getResponse(Long chatRoomId) {

        return connect(chatRoomId);
    }

    private String connect(Long chatRoomId){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")  //todo
                .path(URL+chatRoomId)
                .encode()
                .build()
                .toUri();

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        return result.getBody();
    }



}
