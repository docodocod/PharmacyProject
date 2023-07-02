package com.example.pharmacyproject.direction.entity;

import com.example.pharmacyproject.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="direction") //테이블 매핑
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

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
