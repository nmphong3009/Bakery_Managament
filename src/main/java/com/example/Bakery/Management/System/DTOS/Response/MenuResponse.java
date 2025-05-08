package com.example.Bakery.Management.System.DTOS.Response;

import com.example.Bakery.Management.System.Entity.SourceImage;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private SourceImage sourceImage;

    private Integer disCount;
}
