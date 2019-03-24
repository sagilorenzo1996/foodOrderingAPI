package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LineItemIterable implements Serializable {

    private Long id;
    private int quantity;
    private Double price;

}

