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
public class KakaoCategorySearchService { //카카오 카테고리 api를 호출하기 위해 만든 클래스
    //이 클래스를 사용시 공공 데이터에서 받아온 데이터가 필요 없다.

    private final KakaoUriBuilderService kakaoUriBuilderService;
    //URI를 만들기 위해 kakaoUriBuilderService를 선언

    private final RestTemplate restTemplate;
    //rest api로 호출하기 위해 restTemplate 선언

    private static final String PHARMACY_CATEGORY = "PM9"; //약국 데이터
    //카카오 카테고리 api 호출 시 카테고리 코드가 필요하므로 코드명을 초기화

    @Value("${KAKAP_REST_API_KEY}") //@Value 어노테이션을 이용하여 환경변수에 저장 되어 있는 api 키 값을 가져올 수 있다.
    private String kakaoRestApiKey;

    public KakaoApiResponseDto requestPharmacyCategorySearch(double latitude, double longitude, double radius) {
        //카테고리 주소 검색 메서드

        URI uri = kakaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, PHARMACY_CATEGORY);
        //미리 생성한 uri 변환 메서드를 사용하여 uri를 초기화

        HttpHeaders headers = new HttpHeaders();
        //헤더에 값을 보내기 위해 헤더 인스턴스 선언

        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK" + kakaoRestApiKey);
        //헤더에 authorization 키 에다가 카카오 api 키 값을 담는다.

        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<HttpHeaders>(headers);
        //헤더에 담은 후 httpEntity에 담아준다.

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
        //restTemplate을 이용하여 uri,메서드 방법, 헤더값,
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
