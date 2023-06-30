package com.example.pharmacyproject.pharmacy.entity;
import com.example.pharmacyproject.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "pharmacy")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //데이터베이스에서 자동으로 번호를 생성 pk값
    private Long id;

    private String pharmacyName; //약국이름
    private String pharmacyAddress; //약국 주소
    private double latitude; // 위도
    private double longitude; // 경도

    public void changePharmacyAddress(String address) {
        this.pharmacyAddress = address;
    }
}