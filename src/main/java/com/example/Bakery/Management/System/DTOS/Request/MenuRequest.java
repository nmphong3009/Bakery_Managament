package com.example.Bakery.Management.System.DTOS.Request;

import java.math.BigDecimal;

import com.example.Bakery.Management.System.Enum.Category;
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

    private BigDecimal priceCost;

    private Category category;

}
