package com.example.Bakery.Management.System.DTOS.Request;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequest {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;

    private Long imageId;

    private Integer disCount;

}
