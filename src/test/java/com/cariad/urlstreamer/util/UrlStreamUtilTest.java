package com.cariad.urlstreamer.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlStreamUtilTest {

    @Mock
    private RestTemplate restTemplate;
    private UrlStreamUtil urlStreamUtil;
    private String validUrl;
    private String inValidUrl;
    private String result;

    @BeforeEach
    void setUp() {
        urlStreamUtil = new UrlStreamUtil(restTemplate);
        validUrl = "http://127.0.0.1:8090/odd";
        inValidUrl = "45145";
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("one");
        jsonArray.add("two");
        jsonArray.add("ten");
        jsonObject.add("strings", jsonArray);
        result = jsonObject.toString();
    }

    @Test
    void isUrlValid() {
        assertEquals(true, urlStreamUtil.isUrlValid(validUrl));
    }

    @Test
    void isUrlNotValid() {
        assertEquals(false, urlStreamUtil.isUrlValid(inValidUrl));
    }

    @Test
    void callUrl() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        when(restTemplate.getForEntity(validUrl, String.class)).thenReturn(responseEntity);
        List<String> list = urlStreamUtil.callUrl(validUrl);
        assertEquals(3, list.size());
        assertEquals("one", list.get(0));
    }

    @Test
    void callInvalidUrl() {
        given(restTemplate.getForEntity(inValidUrl, String.class)).willAnswer(i -> {
            throw new Exception("Timeout");
        });
        List<String> list = urlStreamUtil.callUrl(inValidUrl);
        assertEquals(0, list.size());

    }
}