package com.example.pharmacyproject.api.service

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.nio.charset.StandardCharsets

@SpringBootTest
class KakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService

    def setup(){
        kakaoUriBuilderService=new KakaoUriBuilderService()
    }

    def "buildUriByAddressSearch-한글 파라미터의 경우 정상적으로 인코딩"(){
        given:
        String address="서울 성북구"
        def charset=StandardCharsets.UTF_8

        when:
        def uri=kakaoUriBuilderService.buildUriByAddressSearch(address)
        def decodeResult=URLDecoder.decode(uri.toString(),charset)

        then:
        System.out.println("uri주소:"+uri)
        decodeResult=="http://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구"
    }
}
