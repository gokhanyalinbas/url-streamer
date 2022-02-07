package com.cariad.urlstreamer.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionResponseTest {

    @Test
    void ExceptionResponseModelTest() {
        ExceptionResponse exceptionResponse = new ExceptionResponse("a", "b");
        exceptionResponse.setReason("z");
        exceptionResponse.setStatus("x");
        assertEquals("z", exceptionResponse.getReason());
        assertEquals("x", exceptionResponse.getStatus());
    }
}