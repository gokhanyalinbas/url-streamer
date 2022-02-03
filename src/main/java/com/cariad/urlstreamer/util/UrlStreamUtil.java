package com.cariad.urlstreamer.util;


import com.cariad.urlstreamer.model.ResponseModel;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UrlStreamUtil {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(UrlStreamUtil.class);


    public boolean isUrlValid(String url) {

        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    public List<String> callUrl(String url) {
        Optional<List<String>> result = Optional.of(new ArrayList<>());
        String response;
        try {

            response = restTemplate.getForEntity(url, String.class).getBody();
            result = Optional.of(new Gson().fromJson(response, ResponseModel.class).getStrings());
        } catch (Exception exception) {
            logger.error("Error occured with URL :" + url);
            logger.error(exception.getMessage());
        }
        return result.get();
    }
}
