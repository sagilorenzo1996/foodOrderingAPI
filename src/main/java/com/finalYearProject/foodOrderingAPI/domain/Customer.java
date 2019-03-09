package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "orders")

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String status;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String telephone;

    @NotNull @Min(16) @Max(19)
    private String cardNumber;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private Date cardExpDate;

    @NotNull @Max(50)
    private String addressLineOne;

    @Max(80)
    private String addressLineTwo;

    @NotNull @Max(50)
    private String city;

    @NotNull
    private int totalOrders;

    @NotNull
    private Date registeredOn;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders;


}
