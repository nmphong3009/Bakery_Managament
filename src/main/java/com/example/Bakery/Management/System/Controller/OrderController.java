package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.DTOS.Request.OrderDetailRequest;
import com.example.Bakery.Management.System.DTOS.Request.OrderRequest;
import com.example.Bakery.Management.System.DTOS.Response.OrderResponse;
import com.example.Bakery.Management.System.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("order")
public class OrderController {
    @Lazy
    private final OrderService orderService;

    @PostMapping("create")
    public ResponseEntity<?> createOrder (@RequestBody OrderRequest orderList){
        return ResponseEntity.ok(orderService.createOrder(orderList));
    }

    @GetMapping("getOrder/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id){
        return orderService.getOrder(id);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<OrderResponse>> getAll(){
        List<OrderResponse> orderResponses = orderService.getAllOrder();
        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("doOrder/{id}")
    public ResponseEntity<OrderResponse> doOrder(@PathVariable Long id){
        return orderService.doOrder(id);
    }

    @PutMapping("doneOrder/{id}")
    public ResponseEntity<?> doneOrder(@PathVariable Long id){
        return orderService.doneOrder(id);
    }
}

