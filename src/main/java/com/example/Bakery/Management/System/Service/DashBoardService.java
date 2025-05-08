package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.Repository.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class DashBoardService {
    @Lazy
    private final OrderRepository orderRepository;

    @Lazy
    private final OrderDetailRepository orderDetailRepository;

    @Lazy
    private final PayRepository payRepository;

    @Lazy
    private final UserService userService;

    @Lazy
    private final UserRepository userRepository;

    @Lazy
    private final MenuRepository menuRepository;

    public DashBoardService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, PayRepository payRepository, UserService userService, UserRepository userRepository, MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.payRepository = payRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    public BigDecimal getRevenueByDate(LocalDate date) {
        return Optional.ofNullable(payRepository.getTotalRevenueByDate(date))
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getRevenueByMonth(int month, int year) {
        return Optional.ofNullable(payRepository.getTotalRevenueByMonth(month, year))
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getRevenueByYear(int year) {
        return Optional.ofNullable(payRepository.getTotalRevenueByYear(year))
                .orElse(BigDecimal.ZERO);
    }

}
