package com.example.pharmacyproject.direction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Base62Service {

    //https://github.com/seruco/base62
    private static final Base62 base62Instance=Baes62.createInstance();

    public String encodeDirectionId(Long directionId){
        return new String(base62Insatnce.encode(String.valueOf(directionId).getBytes()));
    }
    public Long decodeDirectionId(String encodeDirectionId){
        String resultDirectionId=new String(base62Instance.decode(encodeDirectionId.getBytes()));
        return Long.valueOf(resultDirectionId);
    }
}
