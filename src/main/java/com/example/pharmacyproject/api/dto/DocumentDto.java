package com.example.pharmacyproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class DocumentDto {

    @JsonProperty("address_name") //json으로 응답 받을때 api에서 보내주는 파라미터값과 매핑 하게 해준다.
    private String addressName;

    @JsonProperty("y")
    private double latitude;

    @JsonProperty("x")
    private double longitude;
}
