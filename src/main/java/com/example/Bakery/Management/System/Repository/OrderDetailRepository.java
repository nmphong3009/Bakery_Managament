package com.example.Bakery.Management.System.Repository;

import com.example.Bakery.Management.System.Entity.Orders;
import com.example.Bakery.Management.System.Entity.OrdersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrdersDetails,Long> {
    List<OrdersDetails> findByOrder(Orders orders);
}

