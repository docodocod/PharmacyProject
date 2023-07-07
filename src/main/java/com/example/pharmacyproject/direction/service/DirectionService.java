package com.example.pharmacyproject.direction.service;

import com.example.pharmacyproject.api.dto.DocumentDto;
import com.example.pharmacyproject.api.service.KakaoCategorySearchService;
import com.example.pharmacyproject.direction.entity.Direction;
import com.example.pharmacyproject.direction.repository.DirectionRepository;
import com.example.pharmacyproject.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    //약국 최대 검색
    private static final int MAX_SEARCH_COUNT=3;
    //반경 10km 이내
    private static final double RADIUS_KM=10.0;

    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";


    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final Base62Service base62Service;

    private final KakaoCategorySearchService kakaoCategorySearchService;

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList){
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        //CollectionUtils.isEmpty()는 java Collection(List, Map, Set)의 종류의 값들의 존재 여부를 판단하는 메서드입니다.
        return directionRepository.saveAll(directionList);
    }

    @Transactional(readOnly = true)
    public String findDirectionUriById(String encodedId){

        Long decodedId=base62Service.decodeDirectionId(encodedId);
        Direction direction=directionRepository.findById(decodedId).orElse(null);
 b
        String params=String.join(",",direction.getTargetPharmacyName(),
                String.valueOf(direction.getTargetLatitude()),String.valueOf(direction.getTargetLongitude()));
        String result=UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL+params)
                .toUriString();
        return result;
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto){ //파라미터는 고객의 위도,경도

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        //거리 계산 알고리즘을 이용하여, 고객과 약국 사이의 거리를 계산하고 sort
        return pharmacySearchService.searchPharmacyDtoList() //공공기관에서 제공 받은 데이터를 이용
                .stream().map(pharmacyDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetPharmacyName(pharmacyDto.getPharmacyName())
                                .targetAddress(pharmacyDto.getPharmacyAddress())
                                .targetLatitude(pharmacyDto.getLatitude())
                                .targetLongitude(pharmacyDto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                pharmacyDto.getLatitude(),pharmacyDto.getLongitude()))
                                .build())
                .filter(direction -> direction.getDistance()<=RADIUS_KM) //조회할 거리 범위 초기화
                .sorted(Comparator.comparing(Direction::getDistance)) //정렬 문법
                .limit(MAX_SEARCH_COUNT) //몇개를 출력할 것인지
                .collect(Collectors.toList());
    };
    // pharmacy search by category kakao api
    public List<Direction> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto) { //카카오api를 이용해서 사용
        if(Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestPharmacyCategorySearch(inputDocumentDto.getLatitude(), inputDocumentDto.getLongitude(), RADIUS_KM)
                .getDocumentList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetPharmacyName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001) // km 단위
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }
    //Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1); //나의 위치 위도
        lon1 = Math.toRadians(lon1); //나의 위치 경도
        lat2 = Math.toRadians(lat2); //약국 위치 위도
        lon2 = Math.toRadians(lon2); //약국 위치 경도

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    };
};
