package com.example.pharmacyproject.api.service;

import com.example.pharmacyproject.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;

    private final RestTemplate restTemplate;

    private static final String PHARMACY_CATEGORY = "PM9"; //약국 데이터

    @Value("${KAKAP_REST_API_KEY}")
    private String kakaoRestApiKey;

    public KakaoApiResponseDto requestPharmacyCategorySearch(double latitude, double longitude, double radius) {

        URI uri = kakaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, PHARMACY_CATEGORY);

        HttpHeaders headers = new HttpHeaders();

        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK" + kakaoRestApiKey);

        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<HttpHeaders>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
    };
};

//카테고리 데이터 코드
 /* MT1	대형마트
    CS2	편의점
    PS3	어린이집, 유치원
    SC4	학교
    AC5	학원
    PK6	주차장
    OL7	주유소, 충전소
    SW8	지하철역
    BK9	은행
    CT1	문화시설
    AG2	중개업소
    PO3	공공기관
    AT4	관광명소
    AD5	숙박
    FD6	음식점
    CE7	카페
    HP8	병원
    PM9	약국*/
