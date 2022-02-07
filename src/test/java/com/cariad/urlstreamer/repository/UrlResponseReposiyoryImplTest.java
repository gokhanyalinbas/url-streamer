package com.cariad.urlstreamer.repository;

import com.cariad.urlstreamer.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UrlResponseReposiyoryImplTest {


    private UrlResponseReposiyoryImpl urlResponseReposiyory;
    private ResponseModel responseModel;

    @BeforeEach
    void setUp() {
        urlResponseReposiyory = new UrlResponseReposiyoryImpl();
        responseModel = ResponseModel.builder()
                .strings(Arrays.asList("one", "two", "five"))
                .build();
    }

    @Test
    void add() {
        urlResponseReposiyory.add(responseModel);

    }

    @Test
    void getSortedList() {
        add();
        assertEquals(3, urlResponseReposiyory.getSortedList().getStrings().size());
        assertEquals("five", urlResponseReposiyory.getSortedList().getStrings().get(0));
        assertEquals("two", urlResponseReposiyory.getSortedList().getStrings().get(2));

    }
}