package com.nagaraju.data.shape.handlers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagaraju.data.shape.core.Data;

@Service
public class RequestHandler {

	@Autowired
	private RestTemplate restTemplate;

	public void send(String url, Data data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(data.getData().toString(), headers);
		restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
	}

}
