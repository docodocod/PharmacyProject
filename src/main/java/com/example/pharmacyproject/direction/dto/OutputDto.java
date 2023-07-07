package com.example.pharmacyproject.direction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {

    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUri;
    private String roadViewUri;
    private String distance;

}
