package com.finalYearProject.foodOrderingAPI.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class LineItemRequestBody implements Serializable {

    private Iterable<LineItemIterable> lineItem;
    private BigDecimal total;
}
