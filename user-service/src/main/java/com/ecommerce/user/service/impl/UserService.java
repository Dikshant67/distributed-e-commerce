package com.ecommerce.user.service.impl;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreatedRequestDto;
import com.ecommerce.user.dto.UserResponseDto;


public interface UserService {
    UserResponseDto register(UserCreatedRequestDto userCreatedRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    UserResponseDto getById(Long id);

}
