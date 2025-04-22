package com.example.Bakery.Management.System.DTOS.Request;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private Long id;
    private String name;
}
