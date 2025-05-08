package com.example.Bakery.Management.System.Repository;

import com.example.Bakery.Management.System.Entity.Orders;
import com.example.Bakery.Management.System.Entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PayRepository extends JpaRepository<Payments,Long> {
    Optional<Payments> findByOrder(Orders orders);

    @Query("SELECT SUM(p.amount) FROM Payments p WHERE p.paymentStatus = 'COMPLETED' AND p.createdAt = :date")
    BigDecimal getTotalRevenueByDate(@Param("date") LocalDate date);

    @Query("SELECT SUM(p.amount) FROM Payments p WHERE p.paymentStatus = 'COMPLETED' AND EXTRACT(MONTH FROM p.createdAt) = :month AND EXTRACT(YEAR FROM p.createdAt) = :year")
    BigDecimal getTotalRevenueByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(p.amount) FROM Payments p WHERE p.paymentStatus = 'COMPLETED' AND EXTRACT(YEAR FROM p.createdAt) = :year")
    BigDecimal getTotalRevenueByYear(@Param("year") int year);

}
