package com.cariad.urlstreamer.repository;

import com.cariad.urlstreamer.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Repository
public class UrlResponseReposiyoryImpl implements UrlResponseRepository {
    private final HashSet<String> db = new HashSet<>();
    private final Logger logger = LoggerFactory.getLogger(UrlResponseReposiyoryImpl.class);

    @Override
    public void add(ResponseModel responseModel) {
        //maybe cacheable?
        responseModel.getStrings().stream()
                .forEach(i -> db.add(i));
        logger.info("ResponseModel added to db.");
    }

    @Override
    public ResponseModel getSortedList() {
        List<String> list = new ArrayList<>(db);
        Collections.sort(list);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setStrings(list);
        return responseModel;
    }
}
