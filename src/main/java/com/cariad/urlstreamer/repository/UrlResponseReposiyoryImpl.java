package com.cariad.urlstreamer.repository;

import com.cariad.urlstreamer.model.ResponseModel;
import org.springframework.stereotype.Repository;

import java.util.TreeSet;
import java.util.stream.Collectors;

@Repository
public class UrlResponseReposiyoryImpl implements UrlResponseRepository {
    private final TreeSet<String> db = new TreeSet<>();

    @Override
    public void add(ResponseModel responseModel) {
        //maybe cacheable?
        responseModel.getStrings().stream()
                .forEach(i -> db.add(i));
    }

    @Override
    public ResponseModel getSortedList() {
        return ResponseModel.builder()
                .strings(db.stream().collect(Collectors.toList()))
                .build();
    }
}
