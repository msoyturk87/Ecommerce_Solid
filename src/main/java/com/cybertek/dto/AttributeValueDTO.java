package com.cybertek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttributeValueDTO {

    private  Long id;
    private AttributeDTO attribute;
    private String description;
    private String name;

}
