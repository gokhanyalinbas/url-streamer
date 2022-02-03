package com.cariad.urlstreamer.service;

import com.cariad.urlstreamer.dto.ResponseDto;

import java.util.List;

public interface UrlStreamService {

    ResponseDto processUrls(List<String> urls);

}
