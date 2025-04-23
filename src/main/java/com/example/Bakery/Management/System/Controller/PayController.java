package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.DTOS.Response.OrderResponse;
import com.example.Bakery.Management.System.Entity.Payments;
import com.example.Bakery.Management.System.Enum.PaymentMethod;
import com.example.Bakery.Management.System.Service.PayService;
import com.example.Bakery.Management.System.Util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@RestController
@AllArgsConstructor
@RequestMapping("pay")
public class PayController {
    @Lazy
    private final PayService payService;

    @PostMapping("{orderId}")
    public ResponseEntity<?> payOrder(
            @PathVariable Long orderId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestParam Boolean useRewardPoints,
            @RequestParam String phoneNumber){
        return ResponseEntity.ok(payService.payOrder(orderId,phoneNumber ,paymentMethod, useRewardPoints));
    }

    @GetMapping("getOrder/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        return payService.getOrder(orderId);
    }

    @GetMapping("/vnpay-payment")
    public ResponseEntity<String> createVNPayQrCode(
            @RequestParam Long orderId,
            HttpServletRequest request) {
        try {
            String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
            String vnpTmnCode = "DY22LRAP";
            String vnpHashSecret = "HKHD4R8TTD9ZM5GDSD9WVP12WLXNNVTD";

            String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());

            Payments payment = new Payments();
            payment.generateTransactionCode();
            String vnp_TxnRef = payment.getTransactionCode();

            Map<String, String> vnpParams = new TreeMap<>();
            vnpParams.put("vnp_Amount", String.valueOf((Integer) (payService.getAmount(orderId).intValue() * 100)));
            vnpParams.put("vnp_Command", "pay");
            vnpParams.put("vnp_CreateDate", vnp_CreateDate);
            vnpParams.put("vnp_CurrCode", "VND");
            vnpParams.put("vnp_ExpireDate", vnp_ExpireDate);
            vnpParams.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_OrderInfo", "Thanh toan cac don hang: " + orderId);
            vnpParams.put("vnp_OrderType", "billpayment");
            vnpParams.put("vnp_ReturnUrl", "http://localhost:4200/client/thanh-toan");
            vnpParams.put("vnp_TmnCode", vnpTmnCode);
            vnpParams.put("vnp_TxnRef", vnp_TxnRef);
            vnpParams.put("vnp_Version", "2.1.0");

            String queryUrl = VNPayUtil.getPaymentURL(vnpParams, true);
            String vnpSecureHash = VNPayUtil.hmacSHA512(vnpHashSecret, queryUrl);
            String paymentUrl = vnpUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnpSecureHash;

            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating payment URL");
        }
    }

}
