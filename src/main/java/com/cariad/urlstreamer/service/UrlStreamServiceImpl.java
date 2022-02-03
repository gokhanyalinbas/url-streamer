package com.cariad.urlstreamer.service;

import com.cariad.urlstreamer.dto.ResponseDto;
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
        ResponseModel responseModel = new ResponseModel();
        for (String url : urls) {

            if (urlStreamUtil.isUrlValid(url)) {
                responseModel.setStrings(urlStreamUtil.callUrl(url));
                urlResponseReposiyory.add(responseModel);
            }
        }
        return modelMapper.map(urlResponseReposiyory.getSortedList(), ResponseDto.class);
    }
}
