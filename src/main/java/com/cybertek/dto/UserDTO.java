package com.cybertek.dto;

import com.cybertek.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private  Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Status status;
    private String phoneNumber;
    private String userName;
    private String email;
    private String password;
    private String address;



}
