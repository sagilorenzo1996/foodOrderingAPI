package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "lineItem")

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @Column(precision = 10,scale = 2)
    private BigDecimal price;

    @NotNull
    @Max(200)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String type;

    @OneToOne(mappedBy = "item",cascade = CascadeType.ALL)
    private LineItem lineItem;
}
