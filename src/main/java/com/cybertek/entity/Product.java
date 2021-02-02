package com.cybertek.entity;

import com.cybertek.enums.ProductCondition;
import com.cybertek.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @DecimalMin("0.00")
    private BigDecimal price;

    @Positive
    private Integer quantity;

    private BigDecimal volume;

    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    private ProductCondition productCondition;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "uom_id")
    private Uom uom;

    @ManyToMany
    @JoinTable(name = "product_sub_category_rel",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_category_id"))
    private List<SubCategory> subCategories; // talk about this part for Set

    @ManyToMany
    @JoinTable(name = "product_attribute_value_rel",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id"))
    private List<AttributeValue> attributeValues;
}
