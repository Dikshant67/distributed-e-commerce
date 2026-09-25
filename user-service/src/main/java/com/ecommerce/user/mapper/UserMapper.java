package com.ecommerce.user.mapper;

import com.ecommerce.user.dto.UserCreatedRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserCreatedRequestDto userCreatedRequestDto) {
        return User.builder()
                .name(userCreatedRequestDto.getName())
                .email(userCreatedRequestDto.getEmail())
                .password(passwordEncoder.encode(userCreatedRequestDto.getPassword()))
                .phone(userCreatedRequestDto.getPhone())
                .roles("ROLE_USER")
                .build();
    }
    public UserResponseDto toDTO(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
