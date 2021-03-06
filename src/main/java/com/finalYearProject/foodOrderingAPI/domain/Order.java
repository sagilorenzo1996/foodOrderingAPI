package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "lineItems")

@Entity
@Table(name = "Item_Order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @Column(precision = 10,scale = 2)
    private BigDecimal total;

    @NotNull
    private String status;

    @NotNull
    private String type;

    @NotNull
    private String promo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date estimatedDeliveryDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private  Date orderedDate;

    @NotNull
    private  String location;

    @NotNull
    private Long customerId;

    @NotNull
    private Long driverId ;

}
