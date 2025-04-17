package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.Entity.Orders;
import com.example.Bakery.Management.System.Entity.Payments;
import com.example.Bakery.Management.System.Enum.PaymentMethod;
import com.example.Bakery.Management.System.Enum.PaymentStatus;
import com.example.Bakery.Management.System.Repository.OrderRepository;
import com.example.Bakery.Management.System.Repository.PayRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PayService {
    @Lazy
    private final PayRepository payRepository;

    @Lazy
    private final OrderRepository orderRepository;

    public PayService(PayRepository payRepository, OrderRepository orderRepository) {
        this.payRepository = payRepository;
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<?> payOrder(Long orderId, PaymentMethod paymentMethod){
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        Payments payments = payRepository.findByOrder(orders)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + orders));
        payments.setPaymentMethod(paymentMethod);
        payments.setPaymentStatus(PaymentStatus.COMPLETED);
        payRepository.save(payments);
        return ResponseEntity.ok("Thanh toán thành công ");
    }
}

