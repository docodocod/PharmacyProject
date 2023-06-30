package com.example.pharmacyproject.direction.service;

import com.example.pharmacyproject.api.dto.DocumentDto;
import com.example.pharmacyproject.direction.entity.Direction;
import com.example.pharmacyproject.pharmacy.dto.PharmacyDto;
import com.example.pharmacyproject.pharmacy.repository.PharmacyRepository;
import com.example.pharmacyproject.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.Document;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    //약국 최대 검색
   private static final int MAX_SEARCH_COUNT=3;
    //반경 10km 이내
   private static final double RADIUS_KM=10.0;


    private final PharmacySearchService pharmacySearchService;

    public List<Direction> buildDirectionList(DocumentDto documentDto){ //파라미터는 고객의 위도,경도

        //약국 데이터 조회
        List<PharmacyDto> pharmacyDtos=pharmacySearchService.searchPharmacyDtoList();
        //거리 계산 알고리즘을 이용하여, 고객과 약국 사이의 거리를 계산하고 sort
        return pharmacySearchService.searchPharmacyDtoList()
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
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), phar)
                                )
                                .build())
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
    }
}
