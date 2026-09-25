package com.ecommerce.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class LoginResponseDto {
    private String token;
    private String tokenType="Bearer";
    private Long userId;
    private String email;
    private String name;

}
