package com.example.Bakery.Management.System.DTOS.Response;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceImageResponse {
    private Long id;
    private String url;
    private String name;
}
