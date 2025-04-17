package com.example.Bakery.Management.System.DTOS.Request;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private Long menuId;
    private Integer quantity;
}
