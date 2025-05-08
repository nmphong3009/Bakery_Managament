package com.example.Bakery.Management.System.Entity;
import com.example.Bakery.Management.System.Enum.PaymentMethod;
import com.example.Bakery.Management.System.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column
    private String transactionCode;

    public void generateTransactionCode() {
        this.transactionCode = "PAY" + String.format("%010d", (int)(Math.random() * 1_000_000_0000L));
    }
}

