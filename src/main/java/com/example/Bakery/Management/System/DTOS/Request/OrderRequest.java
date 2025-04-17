package com.example.Bakery.Management.System.DTOS.Request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    List<OrderDetailRequest> orderDetailRequestList;

    private String address;

    private String phoneNumber;
}
