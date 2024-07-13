package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.PaymentRequestDto;
import com.example.ZeBank.Dto.Response.PaymentResponseDto;
import com.example.ZeBank.EntityLayer.Payment;
import com.example.ZeBank.Service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/payments")
public class PaymentController extends GenericControllerImpl<Payment, PaymentRequestDto, PaymentResponseDto> {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        super(paymentService);
        this.paymentService = paymentService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAll() {
        logger.info("Fetching all payments");
        List<PaymentResponseDto> paymentResponseDto = paymentService.findAll();
        logger.info("Fetched {} payments", paymentResponseDto.size());
        return ResponseEntity.ok(paymentResponseDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<PaymentResponseDto>> getById(@PathVariable Long id) {
        logger.info("Fetching payment with ID: {}", id);
        Optional<List<PaymentResponseDto>> paymentResponseDto = paymentService.findById(id);
        if (paymentResponseDto.isPresent()) {
            logger.info("Fetched payment with ID: {}", id);
            return ResponseEntity.ok(paymentResponseDto.get());
        } else {
            logger.warn("Payment with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(@RequestBody PaymentRequestDto entity) {
        logger.info("Creating a new payment");
        PaymentResponseDto paymentResponseDto = paymentService.save(entity);
        logger.info("Created a new payment with ID: {}", paymentResponseDto.getPaymentId());
        return ResponseEntity.ok(paymentResponseDto);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> update(@PathVariable Long id, @RequestBody PaymentRequestDto entity) {
        logger.info("Updating payment with ID: {}", id);
        PaymentResponseDto paymentResponseDto = paymentService.update(id, entity);
        logger.info("Updated payment with ID: {}", id);
        return ResponseEntity.ok(paymentResponseDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting payment with ID: {}", id);
        paymentService.delete(id);
        logger.info("Deleted payment with ID: {}", id);
        return ResponseEntity.ok("Payment deleted successfully");
    }
}
