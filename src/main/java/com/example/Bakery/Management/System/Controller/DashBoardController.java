package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.Service.DashBoardService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("dashboard/admin")
public class DashBoardController {
    @Lazy
    private final DashBoardService dashBoardService;

    @GetMapping("/day")
    public ResponseEntity<?> getRevenueByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(dashBoardService.getRevenueByDate(date));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getRevenueByMonth(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(dashBoardService.getRevenueByMonth(month, year));
    }

    @GetMapping("/year")
    public ResponseEntity<?> getRevenueByYear(
            @RequestParam int year) {
        return ResponseEntity.ok(dashBoardService.getRevenueByYear(year));
    }
}
