package com.cariad.urlstreamer.service;

import com.cariad.urlstreamer.constant.ApplicationConstant;
import com.cariad.urlstreamer.dto.ResponseDto;
import com.cariad.urlstreamer.exception.InputSizeLimitExceededException;
import com.cariad.urlstreamer.model.ResponseModel;
import com.cariad.urlstreamer.repository.UrlResponseReposiyoryImpl;
import com.cariad.urlstreamer.util.UrlStreamUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlStreamServiceImpl implements UrlStreamService {
    private final UrlResponseReposiyoryImpl urlResponseReposiyory;
    private final ModelMapper modelMapper;
    private final UrlStreamUtil urlStreamUtil;

    @Override
    public ResponseDto processUrls(List<String> urls) {
        valideInput(urls);
        for (String url : urls) {

            if (urlStreamUtil.isUrlValid(url)) {
                urlResponseReposiyory.add(ResponseModel.builder()
                        .strings(urlStreamUtil.callUrl(url))
                        .build());
            }
        }
        return modelMapper.map(urlResponseReposiyory.getSortedList(), ResponseDto.class);
    }

    private void valideInput(List<String> urls) {

        if (urls.size() > ApplicationConstant.MAX_SIZE)
            throw new InputSizeLimitExceededException("Input size can be max " + ApplicationConstant.MAX_SIZE + ". Your input size :" + urls.size());
    }
}
