package com.example.Bakery.Management.System.Repository;

import com.example.Bakery.Management.System.Entity.Orders;
import com.example.Bakery.Management.System.Entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayRepository extends JpaRepository<Payments,Long> {
    Optional<Payments> findByOrder(Orders orders);
}
