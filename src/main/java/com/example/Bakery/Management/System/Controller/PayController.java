package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.Enum.PaymentMethod;
import com.example.Bakery.Management.System.Service.PayService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("pay")
public class PayController {
    @Lazy
    private final PayService payService;

    @PostMapping("pay/{id}")
    public ResponseEntity<?> payOrder(@PathVariable Long orderId, @RequestParam PaymentMethod paymentMethod){
        return ResponseEntity.ok(payService.payOrder(orderId, paymentMethod));
    }
}
