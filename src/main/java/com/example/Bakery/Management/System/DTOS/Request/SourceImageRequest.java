package com.example.Bakery.Management.System.DTOS.Request;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceImageRequest {
    private Long id;
    private String url;
    private String name;
}
