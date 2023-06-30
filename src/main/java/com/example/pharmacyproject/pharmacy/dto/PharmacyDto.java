package com.example.pharmacyproject.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDto{
    private Long id;
    private String pharmacyName; //약국이름
    private String pharmacyAddress; //약국 주소
    private double latitude; // 위도
    private double longitude; // 경도
}
