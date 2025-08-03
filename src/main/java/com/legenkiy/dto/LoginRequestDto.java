package com.legenkiy.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {

    private String username;
    private String password;

}
