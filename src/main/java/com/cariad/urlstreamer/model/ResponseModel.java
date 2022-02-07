package com.cariad.urlstreamer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ResponseModel {

    private List<String> strings;
}
