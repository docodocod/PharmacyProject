package com.example.pharmacyproject.pharmacy.service;

import com.example.pharmacyproject.pharmacy.dto.PharmacyDto;
import com.example.pharmacyproject.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService { //그냥 repositoryService에서 findAll로 불러올 수 있지만 redis 사용을 위해 메서드 만듦

    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDto> searchPharmacyDtoList(){

        //redis

        //db
        return pharmacyRepositoryService.findAll()
                .stream()
                .map(this::convertToPharmacyDto)
                .collect(Collectors.toList());
    }
    //왜 convertToPharmacyDto를 만들었는가?
    //Entity는 db에 직접적인 영향을 미치므로 Entity로 넘김 후 dto로 전달해서 dto로 값을 주고 받는다.
    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy){
        //레디스를 사용할 떄는 바로 entity를 사용할 수 없기 때문에 dto로 변환해서 넘겨준다.

        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .pharmacyName(pharmacy.getPharmacyName())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }
}
