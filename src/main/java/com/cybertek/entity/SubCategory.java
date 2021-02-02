package com.cybertek.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sub_categories",uniqueConstraints ={@UniqueConstraint(columnNames  ={"name","category_id"})})
@Where(clause = "is_deleted=false")
public class SubCategory extends BaseEntity<Integer> {

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
