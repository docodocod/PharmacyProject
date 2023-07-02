package com.example.pharmacyproject.pharmacy.service;

import com.example.pharmacyproject.api.dto.DocumentDto;
import com.example.pharmacyproject.api.dto.KakaoApiResponseDto;
import com.example.pharmacyproject.api.service.KakaoAddressSearchService;
import com.example.pharmacyproject.api.service.KakaoUriBuilderService;
import com.example.pharmacyproject.direction.entity.Direction;
import com.example.pharmacyproject.direction.service.DirectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacyList(String address){
        KakaoApiResponseDto kakaoApiResponseDto= kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())){
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}",address);
            return;
        }

        DocumentDto documentDto=kakaoApiResponseDto.getDocumentList().get(0);
//공공 데이터에서 제공 받은 db로 수행
//        List<Direction> directionList=directionService.buildDirectionList(documentDto);

        //db에 저장되어 있는 데이터가 아닌 카카dh api를 이용하여 검색
        List<Direction> directionList=directionService.buildDirectionListByCategoryApi(documentDto);

        directionService.saveAll(directionList);
    };
};
