package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data

@Entity
@Table(name = "LineItem")
public class LineItem {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @Column(precision = 10,scale = 2)
    private BigDecimal price;

    @NotNull
    private int quantity;

    @NotNull
    @Max(15)
    private String status;

    @ManyToOne
    @JoinColumn
    private Order order;

    @OneToOne
    @JoinColumn
    private Item item;

    @OneToOne
    @JoinColumn
    private Employee employee;

}
