package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "lineItems")

@Entity
@Table(name = "Item_Order")
public class Order {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @Column(precision = 10,scale = 2)
    private BigDecimal total;

    @NotNull @Max(20)
    private String status;

    @NotNull @Max(20)
    private String type;

    @NotNull @Max(30)
    private String promo;

    @Future
    private Date estimatedDeliveryDate;

    @NotNull
    private  Date orderedDate;

    @NotNull
    private  String location;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Driver driver;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<LineItem> lineItems;

}
