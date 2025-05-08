package com.example.Bakery.Management.System.Entity;

import com.example.Bakery.Management.System.Enum.OrdersStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "orders")
public class Orders extends AbstractEntity<Long> {

    @Column
    private BigDecimal totalPrice;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrdersStatus ordersStatus;

}

