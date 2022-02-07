package com.cariad.urlstreamer.service;

import com.cariad.urlstreamer.dto.ResponseDto;
import com.cariad.urlstreamer.exception.InputSizeLimitExceededException;
import com.cariad.urlstreamer.model.ResponseModel;
import com.cariad.urlstreamer.repository.UrlResponseReposiyoryImpl;
import com.cariad.urlstreamer.util.UrlStreamUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UrlStreamServiceImplTest {

    @Mock
    private UrlResponseReposiyoryImpl urlResponseReposiyory;
    private UrlStreamServiceImpl urlStreamService;
    @Autowired
    private ModelMapper modelMapper;
    @Mock
    private UrlStreamUtil urlStreamUtil;
    private List<String> urls;
    private List<String> response;
    private ResponseModel responseModel;

    @BeforeEach
    void setUp() {
        urlStreamService = new UrlStreamServiceImpl(urlResponseReposiyory, modelMapper, urlStreamUtil);
        urls = Arrays.asList("http://127.0.0.1:8090/odd", "http://127.0.0.1:8090/fibo");
        response = Arrays.asList("one", "two", "ten");
        responseModel = ResponseModel.builder()
                .strings(Arrays.asList("one", "ten", "two"))
                .build();
    }

    @Test
    void processUrls() {
        when(urlStreamUtil.isUrlValid(any(String.class))).thenReturn(true);
        doNothing().when(urlResponseReposiyory).add(any(ResponseModel.class));
        when(urlStreamUtil.callUrl(any(String.class))).thenReturn(response);
        when(urlResponseReposiyory.getSortedList()).thenReturn(responseModel);
        ResponseDto responseDto = urlStreamService.processUrls(urls);
        assertThat(responseDto).isNotNull();
        assertEquals(3, responseDto.getStrings().size());
    }

    @Test
    void validateInput() {
        urls = Arrays.asList("http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd",
                "http://127.0.0.1:8090/odd");

        // urls.size()==11
        InputSizeLimitExceededException exception = assertThrows(InputSizeLimitExceededException.class, () -> {
            urlStreamService.processUrls(urls);
        });

        assertThat(exception).hasMessageContaining("Input size can be max").isInstanceOf(InputSizeLimitExceededException.class);

    }

}