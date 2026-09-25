package com.ecommerce.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatedRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Please Enter Valid Email")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password Cannot be blank")
    @Size(min = 6,message = "Password must be atleast 6 chars long")
    private String password;
    private String phone;

}
