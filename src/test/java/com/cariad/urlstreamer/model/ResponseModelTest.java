package com.cariad.urlstreamer.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseModelTest {

    @Test
    void ResponseModelGetterSetterTest() {
        ResponseModel responseModel = new ResponseModel(Arrays.asList("one"));
        responseModel.setStrings(Arrays.asList("one", "two"));
        assertEquals(2, responseModel.getStrings().size());

    }
}