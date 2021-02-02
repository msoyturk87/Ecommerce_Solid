package com.cybertek.dto;

import com.cybertek.enums.ProductCondition;
import com.cybertek.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @DecimalMin("0.00")
    private BigDecimal price;

    @Positive
    private Integer quantity;
    private BigDecimal volume;
    private BigDecimal weight;
    private ProductCondition productCondition;
    private Status status;
    private CurrencyDTO currency;
    private UomDTO uom;
    private List<SubCategoryDTO> subCategories;
    private List<AttributeValueDTO> attributeValues;
}
