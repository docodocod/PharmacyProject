package com.example.pharmacyproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    } //redis를 사용하기 위해 데이터들을 맵핑하기 위해
}
