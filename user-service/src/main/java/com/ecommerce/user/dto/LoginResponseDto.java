package com.ecommerce.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Builder
public class LoginResponseDto {
    private String token;
    private String tokenType="Bearer";
    private Long userId;
    private String email;
    private String name;

}
