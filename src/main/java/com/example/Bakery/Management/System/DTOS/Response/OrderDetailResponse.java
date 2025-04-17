package com.example.Bakery.Management.System.DTOS.Response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private String name;
    private Integer quantity;
    private BigDecimal price;
}
