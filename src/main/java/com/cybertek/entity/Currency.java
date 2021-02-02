package com.cybertek.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "currencies",uniqueConstraints ={@UniqueConstraint(columnNames  ={"name","symbol"})})
@Where(clause = "is_deleted=false")
public class Currency extends BaseEntity<Integer> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String symbol;
}
