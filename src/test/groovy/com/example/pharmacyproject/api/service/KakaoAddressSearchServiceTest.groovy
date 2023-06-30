package com.example.pharmacyproject.api.service

import com.example.pharmacyproject.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import spock.lang.Specification

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def"address 파라미터 없이 null이면, requestAddressSearch 메소드는 null을 반환한다."(){
        given:
        String address=null

        when:
        def result=kakaoAddressSearchService.requestAddressSearch((address))

        then:
        result == null
    }

    def"주소값이 valid 한다면, requestAddressSearch 메소드는 정상적으로 document를 반환한디."(){
        given:
        def address="서울 성북구 종암로 18길"

        when:
        def result=kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentDto.size()>0
        result.metaDto.totalCount>0
        result.documentDto.get(0).addressName!=null
    }
}
