package com.cybertek.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "unit_of_measures")
@Where(clause = "is_deleted=false")
public class Uom extends BaseEntity<Integer>{

    @Column(unique = true,nullable = false)
    private String name;

    private String description;
}
