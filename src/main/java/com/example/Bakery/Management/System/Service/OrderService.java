package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.DTOS.Request.OrderRequest;
import com.example.Bakery.Management.System.DTOS.Response.OrderDetailResponse;
import com.example.Bakery.Management.System.DTOS.Response.OrderResponse;
import com.example.Bakery.Management.System.Entity.MenuItems;
import com.example.Bakery.Management.System.Entity.Orders;
import com.example.Bakery.Management.System.Entity.OrdersDetails;
import com.example.Bakery.Management.System.Entity.Payments;
import com.example.Bakery.Management.System.Enum.OrdersStatus;
import com.example.Bakery.Management.System.Enum.PaymentStatus;
import com.example.Bakery.Management.System.Repository.MenuRepository;
import com.example.Bakery.Management.System.Repository.OrderDetailRepository;
import com.example.Bakery.Management.System.Repository.OrderRepository;
import com.example.Bakery.Management.System.Repository.PayRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Lazy
    private final OrderRepository orderRepository;

    @Lazy
    private final MenuRepository menuRepository;

    @Lazy
    private final OrderDetailRepository orderDetailRepository;

    @Lazy
    private final PayRepository payRepository;

    @Lazy
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, MenuRepository menuRepository, OrderDetailRepository orderDetailRepository, PayRepository payRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.payRepository = payRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> createOrder (OrderRequest orderRequest){

        Orders orders = Orders.builder()
                .totalPrice(BigDecimal.ZERO)
                .user(userService.getCurrentUser())
                .phoneNumber(orderRequest.getPhoneNumber())
                .address(orderRequest.getAddress())
                .ordersStatus(OrdersStatus.PENDING)
                .build();
        orderRepository.save(orders);
        List<OrdersDetails> orderDetailsList = orderRequest.getOrderDetailRequestList().stream().map(item -> {
            MenuItems menuItems = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + item.getMenuId()));

            return OrdersDetails.builder()
                    .order(orders)
                    .menuItems(menuItems)
                    .quantity(item.getQuantity())
                    .build();
        }).collect(Collectors.toList());
        BigDecimal totalPrice = orderRequest.getOrderDetailRequestList().stream()
                .map(item -> {
                    MenuItems menuItems = menuRepository.findById(item.getMenuId())
                            .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + item.getMenuId()));
                    return menuItems.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orders.setTotalPrice(totalPrice);
        orderRepository.save(orders);
        orderDetailRepository.saveAll(orderDetailsList);
        Payments payments = payRepository.findByOrder(orders)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + orders));
        payments.setAmount(orders.getTotalPrice());
        payments.setPaymentStatus(PaymentStatus.PENDING);
        payRepository.save(payments);
        return ResponseEntity.ok("Order created successfully!");
    }

    public ResponseEntity<OrderResponse> getOrder(Long id){
        Orders orders = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        List<OrderDetailResponse> orderDetailResponses = orderDetailRepository.findByOrder(orders).stream()
                .map(item -> new OrderDetailResponse(
                        item.getMenuItems().getName(),
                        item.getQuantity(),
                        item.getMenuItems().getPrice()
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(OrderResponse.builder()
                .id(orders.getId())
                .totalPrice(orders.getTotalPrice())
                .orderDetailResponseList(orderDetailResponses)
                .address(orders.getAddress())
                .phoneNumber(orders.getPhoneNumber())
                .ordersStatus(orders.getOrdersStatus())
                .build(), HttpStatus.OK);
    }

    public List<OrderResponse> getAllOrder(){
        List<Orders> ordersList = orderRepository.findAll();
        return ordersList.stream().map(orders -> {
            List<OrderDetailResponse> orderDetailResponses = orderDetailRepository.findByOrder(orders).stream()
                    .map(item -> new OrderDetailResponse(
                            item.getMenuItems().getName(),
                            item.getQuantity(),
                            item.getMenuItems().getPrice()
                    ))
                    .collect(Collectors.toList());
            return new OrderResponse(
                    orders.getId(),
                    orders.getTotalPrice(),
                    orders.getAddress(),
                    orders.getPhoneNumber(),
                    orders.getOrdersStatus(),
                    orderDetailResponses
            );
        }).collect(Collectors.toList());
    }

    public ResponseEntity<OrderResponse> doOrder(Long id){
        Orders orders = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orders.setOrdersStatus(OrdersStatus.PREPARING);
        orderRepository.save(orders);
        List<OrderDetailResponse> orderDetailResponses = orderDetailRepository.findByOrder(orders).stream().map(
                item -> OrderDetailResponse.builder()
                        .name(item.getMenuItems().getName())
                        .quantity(item.getQuantity())
                        .build()
        ).toList();
        return new ResponseEntity<>(OrderResponse.builder()
                .id(orders.getId())
                .ordersStatus(orders.getOrdersStatus())
                .orderDetailResponseList(orderDetailResponses)
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<?> doneOrder(Long id){
        Orders orders = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orders.setOrdersStatus(OrdersStatus.COMPLETED);
        orderRepository.save(orders);
        return ResponseEntity.ok("Đã hoàn thành");
    }

}

