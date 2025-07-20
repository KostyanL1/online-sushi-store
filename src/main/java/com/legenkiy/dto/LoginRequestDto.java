package com.legenkiy.dto;


import lombok.*;
import org.hibernate.event.spi.PreInsertEvent;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {

    private String username;
    private String password;

}
