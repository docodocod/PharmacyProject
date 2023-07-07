package com.example.pharmacyproject.direction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectionDto {

    // 고객
    private String inputAddress; //검색 주소
    private double inputLatitude; //y 좌표
    private double inputLongitude; //x 좌표

    // 약국
    private String targetPharmacyName; //주변 약국 이름
    private String targetAddress; //주변 약국 주소
    private double targetLatitude; // 주변 약국 y 좌표
    private double targetLongitude; // 주변 약국 x 좌표

    //고객 주소와 약국 주소 사이의 거리
    private double distance;
}
