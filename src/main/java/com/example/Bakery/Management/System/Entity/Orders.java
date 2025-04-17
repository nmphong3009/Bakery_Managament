package com.example.Bakery.Management.System.Entity;

import com.example.Bakery.Management.System.Enum.OrdersStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private OrdersStatus ordersStatus;

}

