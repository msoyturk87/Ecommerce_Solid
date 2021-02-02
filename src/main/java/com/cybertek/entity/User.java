package com.cybertek.entity;

import com.cybertek.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
// no need @Where because no delete option
public class User extends BaseEntity<Long>{

    private String firstName;
    private String lastName;

    private LocalDate birthDate;
    // no need to columnDef as a DATE in this cause of Java 8
    @Enumerated(EnumType.STRING)
    private Status status;

    private String phoneNumber;

    @Column(unique = true,nullable = false)
    private String userName;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;


    private String address;



}
