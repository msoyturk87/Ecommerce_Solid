package com.cybertek.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "attribute_values")
public class AttributeValue extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name="attribute_id")
    private Attribute attribute;

    private String description;

    private String name;

}
