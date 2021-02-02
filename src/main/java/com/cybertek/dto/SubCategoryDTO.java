package com.cybertek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubCategoryDTO {

    private  Integer id;
    private String name;
    private CategoryDTO category;
}
