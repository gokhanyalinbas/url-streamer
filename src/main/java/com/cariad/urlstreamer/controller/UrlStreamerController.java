package com.cariad.urlstreamer.controller;

import com.cariad.urlstreamer.constant.ApplicationConstant;
import com.cariad.urlstreamer.dto.ResponseDto;
import com.cariad.urlstreamer.service.UrlStreamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApplicationConstant.REQUEST_MAPPING)
@RequiredArgsConstructor
public class UrlStreamerController {
    private final UrlStreamServiceImpl urlStreamService;

    @GetMapping()
    public ResponseEntity<ResponseDto> streamUrls(@RequestParam(name = "u") List<String> strings) {
        return new ResponseEntity<>(urlStreamService.processUrls(strings), HttpStatus.OK);
    }

}
