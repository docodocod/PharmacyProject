package com.example.pharmacyproject.api.service;

import com.example.pharmacyproject.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpHeaders;

@Slf4j //로그를 위해서 선언
@RequiredArgsConstructor //생성자주입을 위해서 선언
@Service
public class KakaoAddressSearchService { //카카오 api에서 호출하고 응답받은 값을 dto에 담아서 return 해주는 서비스

    private final RestTemplate restTemplate; //카카오 REST API를 사용하기 위해서 restTemplate 인스턴스 선언

    private final KakaoUriBuilderService kakaoUriBuilderService; //의존성 주입

    @Value("${kakao.rest.api.key}") //환경변수에 있는 값을 가져오기 위해 @Value 선언
    private String kakaoRestApiKey;

    @Retryable(
            value={RuntimeException.class},
            maxAttempts =2,
            backoff = @Backoff(delay=2000)
    )

    public KakaoApiResponseDto requestAddressSearch(String address){
        //address를 파라미터로 받고 api 호출 후 응답받은 값을 kakaoApiResponseDtoㄹ 객체에 담는다.

        if(ObjectUtils.isEmpty(address)) return null; //인자 값이 null일 경우 null 반환

        URI uri=kakaoUriBuilderService.buildUriByAddressSearch(address);
        //buildUriAddressSearch를 이용하여 URI 생성

        org.springframework.http.HttpHeaders headers=new org.springframework.http.HttpHeaders();
        //헤더에다가 정보를 담아서 요청하기 위해 헤더 인스턴스 선언

        headers.set(org.springframework.http.HttpHeaders.AUTHORIZATION,"KakaoAK"+kakaoRestApiKey);
        //헤더 값에 AUTHORIZATION인 카카오 API KEY 값을 넣어줌

        HttpEntity<HttpHeaders> httpEntity=new HttpEntity<>(headers);
        //헤더값을 httpEntity에다가 저장

        //kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET,httpEntity,KakaoApiResponseDto.class).getBody();
    };

    @Recover
    public KakaoApiResponseDto recover(RuntimeException e, String address) {
        log.error("All the retries failed. address: {}, error : {}", address, e.getMessage());
        return null;
    }
}
