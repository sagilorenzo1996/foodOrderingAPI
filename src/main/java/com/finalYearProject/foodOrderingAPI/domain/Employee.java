package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "lineItem")

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String status;

    @NotNull
    private String position;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String telephone;

    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private LineItem lineItem;
}
