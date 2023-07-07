package com.example.pharmacyproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class KakaoApiResponseDto {

    @JsonProperty("meta")
    private MetaDto metaDto; //카카오 API 받아오는 META 데이터를 받기 위해 객체로 선언

    @JsonProperty("documents")
    private List<DocumentDto> documentList; //document는 리스트로 받아올 것이기 때문에 list로 반환

}
