package com.cariad.urlstreamer.controller;

import com.cariad.urlstreamer.constant.ApplicationConstant;
import com.cariad.urlstreamer.dto.ResponseDto;
import com.cariad.urlstreamer.exception.InputSizeLimitExceededException;
import com.cariad.urlstreamer.service.UrlStreamServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({UrlStreamerController.class})
class UrlStreamerControllerTest {

    @MockBean
    private UrlStreamServiceImpl urlStreamService;
    private List<String> input;
    private ResponseDto responseDto;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        responseDto = new ResponseDto();
        responseDto.setStrings(Arrays.asList("one", "two", "ten"));
        input = Arrays.asList("http://127.0.0.1:8090/odd", "http://127.0.0.1:8090/fibo");
    }

    @Test
    void streamUrls() throws Exception {
        when(urlStreamService.processUrls(input)).thenReturn(responseDto);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("u", input);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING)
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseDto)));

    }

    @Test
    void invalidInputSize() throws Exception {
        when(urlStreamService.processUrls(input)).thenThrow(new InputSizeLimitExceededException("size must be max 10"));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("u", input);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING)
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.reason").exists());

    }

    @Test
    void allOtherException() throws Exception {
        when(urlStreamService.processUrls(input)).thenThrow(new IllegalAccessError("Exception occured"));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("u", input);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING)
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.reason").exists());

    }
}