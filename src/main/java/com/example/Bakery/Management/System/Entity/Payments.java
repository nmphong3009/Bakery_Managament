package com.example.Bakery.Management.System.Entity;
import com.example.Bakery.Management.System.Enum.PaymentMethod;
import com.example.Bakery.Management.System.Enum.PaymentStatus;
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
@Table(name = "payments")
public class Payments extends AbstractEntity<Long>{

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @Column
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}

