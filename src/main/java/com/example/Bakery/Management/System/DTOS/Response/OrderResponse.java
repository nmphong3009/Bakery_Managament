package com.example.Bakery.Management.System.DTOS.Response;

import com.example.Bakery.Management.System.Enum.OrdersStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private  Long id;

    private BigDecimal totalPrice;

    private String address;

    private String phoneNumber;

    private OrdersStatus ordersStatus;

    private List<OrderDetailResponse> orderDetailResponseList;

}
