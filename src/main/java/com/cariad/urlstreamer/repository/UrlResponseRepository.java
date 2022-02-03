package com.cariad.urlstreamer.repository;

import com.cariad.urlstreamer.model.ResponseModel;

public interface UrlResponseRepository {
    void add(ResponseModel responseModel);

    ResponseModel getSortedList();
}
