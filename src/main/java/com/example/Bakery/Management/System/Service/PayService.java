package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.DTOS.Response.OrderDetailResponse;
import com.example.Bakery.Management.System.DTOS.Response.OrderResponse;
import com.example.Bakery.Management.System.Entity.*;
import com.example.Bakery.Management.System.Enum.PaymentMethod;
import com.example.Bakery.Management.System.Enum.PaymentStatus;
import com.example.Bakery.Management.System.Repository.OrderDetailRepository;
import com.example.Bakery.Management.System.Repository.OrderRepository;
import com.example.Bakery.Management.System.Repository.PayRepository;
import com.example.Bakery.Management.System.Repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayService {
    @Lazy
    private final PayRepository payRepository;

    @Lazy
    private final OrderRepository orderRepository;

    @Lazy
    private final UserRepository userRepository;

    @Lazy
    private final OrderDetailRepository orderDetailRepository;

    public PayService(PayRepository payRepository, OrderRepository orderRepository, UserRepository userRepository, OrderDetailRepository orderDetailRepository) {
        this.payRepository = payRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public ResponseEntity<?> payOrder(Long orderId, String phoneNumber ,PaymentMethod paymentMethod, Boolean useRewardPoints){
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        Payments payments = payRepository.findByOrder(orders)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + orders));
        User user = userRepository.findByPhoneNumber(phoneNumber);
        orders.setUser(user);
        payments.setPaymentMethod(paymentMethod);
        payments.setPaymentStatus(PaymentStatus.COMPLETED);
        BigDecimal originalTotalPrice = orders.getTotalPrice();
        BigDecimal totalPrice = orders.getTotalPrice();
        if (user != null) {
            Integer availablePoints = user.getRewardPoints();
            if (useRewardPoints) {
                if (availablePoints >= totalPrice.intValue()){
                    totalPrice = BigDecimal.ZERO;
                    user.setRewardPoints(availablePoints - totalPrice.intValue());
                } else {
                    totalPrice = totalPrice.subtract(BigDecimal.valueOf(availablePoints));
                    user.setRewardPoints(0);
                }
            }
            userRepository.save(user);
            Integer earnedPoints = originalTotalPrice.multiply(BigDecimal.valueOf(0.1)).intValue();
            user.setRewardPoints(user.getRewardPoints() + earnedPoints);
        }

        // Cập nhật trạng thái thanh toán
        payments.setAmount(totalPrice);
        payRepository.save(payments);

        // Lưu lại thông tin thanh toán đã cập nhật
        orderRepository.save(orders);

        // Trả về phản hồi thanh toán thành công
        return ResponseEntity.ok("Thanh toán thành công");
    }

    public BigDecimal getAmount(Long orderId){
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        Payments payments = payRepository.findByOrder(orders)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + orders));
        payments.setPaymentStatus(PaymentStatus.COMPLETED);
        payRepository.save(payments);
        return payments.getAmount();
    }

    public ResponseEntity<OrderResponse> getOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        List<OrdersDetails> orderDetailsList = orderDetailRepository.findByOrder(orders);

        Map<MenuItems, Integer> quantityMap = orderDetailsList.stream()
                .collect(Collectors.groupingBy(
                        OrdersDetails::getMenuItems,
                        Collectors.summingInt(OrdersDetails::getQuantity)
                ));
        List<OrderDetailResponse> detailResponses = quantityMap.entrySet().stream()
                .map(entry -> OrderDetailResponse.builder()
                        .name(entry.getKey().getName())
                        .price(entry.getKey().getPrice())
                        .quantity(entry.getValue())
                        .build())
                .collect(Collectors.toList());
        OrderResponse response = OrderResponse.builder()
                .id(orders.getId())
                .totalPrice(orders.getTotalPrice())
                .ordersStatus(orders.getOrdersStatus())
                .orderDetailResponseList(detailResponses)
                .build();
        return ResponseEntity.ok(response);
    }
}

