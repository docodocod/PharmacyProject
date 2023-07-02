package com.example.pharmacyproject.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService { //restTemplate에서 uri를 사용하기 위해서 메서드를 만듦

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL="http://dapi.kakao.com/v2/local/search/address.json";

    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL="http://dapi.kakao.com/v2/local/search/category.json";


    //요청할 api주소는 바뀌지 않을 것이기 때문에 static final로 선언

    public URI buildUriByAddressSearch(String address){
        UriComponentsBuilder uriBuilder= UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        //가독성 있게 uri를 만들기 위해 uriComponentsBuilder 사용

        uriBuilder.queryParam("query",address);
        //queryPram을 이용하여 query에다가 address값을 담아서 전송

        URI uri=uriBuilder.build().encode().toUri(); //.encode()는 브라우저로 요청할 때 utf_8로 인코딩해서 uri 만들어준다.

        log.info("[KakaoUriBuilderService buildUriByAddressSearch] address: {}, uri:{}",address,uri);

        return uri;
    }

    public URI buildUriByCategorySearch(double latitude,double longitude, double radius, String category){

        double meterRadius=radius*1000;

        UriComponentsBuilder uriBuilder=UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_code",category);
        uriBuilder.queryParam("x",longitude);
        uriBuilder.queryParam("y",latitude);
        uriBuilder.queryParam("radius",meterRadius);
        uriBuilder.queryParam("sort","distance");

        URI uri=uriBuilder.build().encode().toUri();

        log.info("[kakaoAddressSearchService buildUriByCategorySearch] uri:{}",uri);

        return uri;
    }
}
