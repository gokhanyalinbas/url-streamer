package com.cariad.urlstreamer.controller;

import com.cariad.urlstreamer.UrlstreamerApplication;
import com.cariad.urlstreamer.constant.ApplicationConstant;
import com.cariad.urlstreamer.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UrlstreamerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    private final String host = "http://localhost:";
    String token = "";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getStream() {
        List<String> input = Arrays.asList("http://127.0.0.1:8090/odd", "http://127.0.0.1:8090/fibo");

        HttpEntity<List<String>> request = new HttpEntity<>(input);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + port + "/" + ApplicationConstant.REQUEST_MAPPING)
                .queryParam("u", input);
        ResponseEntity<ResponseDto> responseEntity = this.restTemplate
                .exchange(builder.build().encode().toUri(), HttpMethod.GET, request, ResponseDto.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

    }
}
