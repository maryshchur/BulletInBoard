package com.example.app.dto;

import com.example.app.entities.User;
import com.example.app.util.validation.EmailExist;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "First name may not be blank")
    @Size(min = 3, max = 20,message = "First name is invalid")
    private String firstName;

    @NotBlank(message = "Last name may not be blank")
    @Size(min = 3, max = 20,message = "Last name is invalid")
    private String lastName;

    @NotBlank(message = "Email may not be blank")
    @EmailExist
    @Email(message = "Email is invalid")
    private String email;

    @Size(min = 6, max = 30,message = "Password must contain at least 6 characters")
    @NotEmpty(message = "Password may not be empty")
    private String password;
    private int amountOfSubscribers;
    private int amountOfSubscriptions;

}
