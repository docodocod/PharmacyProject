package com.example.pharmacyproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration //bean에 등록해주기 위해 @configureation annotation 선언
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(); //bean 설정 후 객체 선언 시 new를 쓰지 않기 위해
        //restApi를 사용하기 위해서 사용
    }
}