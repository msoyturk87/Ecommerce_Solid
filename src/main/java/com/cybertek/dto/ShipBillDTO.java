package com.cybertek.dto;

import com.cybertek.enums.ShipBillStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShipBillDTO {

    private  Long id;

    private String street1;

    private String street2;

    private String country;

    private String state;

    private String city;

    private UserDTO user;

    private ShipBillStatus status;

}