package com.ecommerce.user.mapper;

import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.entity.User;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public LoginResponseDto toLoginResponseDto(User user , String token){
        return LoginResponseDto.builder()
                .email(user.getEmail())
                .token(token)
                .tokenType("Bearer")
                .name(user.getName())
                .build();

    }
}
