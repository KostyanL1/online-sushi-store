package com.legenkiy.util;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class  JwtUtils {

    @Value("${jwt.secret}")
    private String  JwtSecretToken;


}
