package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Request.PaymentRequestDto;
import com.example.ZeBank.Dto.Response.PaymentResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.PaymentStatus;
import com.example.ZeBank.EntityLayer.Enum.PaymentType;
import com.example.ZeBank.EntityLayer.Payment;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentMapper {
    private final CustomerRepository customerRepository;
    private static PaymentMapper instance;

    public PaymentMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public static synchronized PaymentMapper getInstance(CustomerRepository customerRepository) {
        if (instance == null) {
            instance = new PaymentMapper(customerRepository);
        }
        return instance;
    }
    public Payment mapToPayment(PaymentRequestDto paymentRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(Long.valueOf(paymentRequest.getCustomerId()));
        Customer customer = customerOptional.orElseThrow(() -> new EntityNotFoundException("Customer Not Found." + customerOptional.get().getId()));
        Integer creditScore = customer.getCreditScore();
        creditScore = creditScore + ((creditScore * 5) / 100);
        customer.setCreditScore(creditScore);
        if (Objects.nonNull(paymentRequest)) {
            Payment payment = new Payment();
            payment.setPaymentType(PaymentType.CREDIT_PAY);
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            payment.setAmount(paymentRequest.getAmount());
            payment.setPaymentDateTime(paymentRequest.getPaymentDateTime());
            payment.setCustomer(customer);
            return payment;
        }
        return null;
    }

    public static PaymentResponseDto mapToPaymentResponseDto(Payment entity) {
        if (Objects.nonNull(entity)) {
            PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
            paymentResponseDto.setPaymentDate(entity.getPaymentDateTime());
            paymentResponseDto.setPaymentPrice(entity.getAmount());
            paymentResponseDto.setPaymentStatus(mapToPaymentStatusResponse(entity.getPaymentStatus()));
            paymentResponseDto.setPaymentType(mapToPaymentTypeResponse(entity.getPaymentType()));
            paymentResponseDto.setInformationMessage("Kredin Taksidiniz Başarıyla Ödendi");
            return paymentResponseDto;
        }
        return null;
    }

    public static List<PaymentResponseDto> mapToPaymentResponseDtoList(List<Payment> paymentList) {
        return paymentList.stream()
                .map(PaymentMapper::mapToPaymentResponseDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static PaymentStatus mapToPaymentStatus(String paymentStatus) {
        if (Objects.nonNull(paymentStatus)) {
            switch (paymentStatus) {
                case "PENDING" -> {
                    return PaymentStatus.PENDING;
                }
                case "COMPLETED" -> {
                    return PaymentStatus.COMPLETED;
                }
                case "FAILED" -> {
                    return PaymentStatus.FAILED;
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }

    public static PaymentType mapToPaymentType(String paymentType) {
        if (Objects.nonNull(paymentType)) {
            switch (paymentType) {
                case "CASH" -> {
                    return PaymentType.CASH;
                }
                case "CREDIT_CARD" -> {
                    return PaymentType.CREDIT_CARD;
                }
                case "BANK_TRANSFER" -> {
                    return PaymentType.BANK_TRANSFER;
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }

    public static String mapToPaymentStatusResponse(PaymentStatus paymentStatus) {
        if (Objects.nonNull(paymentStatus)) {
            switch (paymentStatus) {
                case PENDING -> {
                    return "Beklemede";
                }
                case COMPLETED -> {
                    return "Tamamlandı";
                }
                case FAILED -> {
                    return "Başarısız";
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }

    public static String mapToPaymentTypeResponse(PaymentType paymentType) {
        if (Objects.nonNull(paymentType)) {
            switch (paymentType) {
                case CASH -> {
                    return "Nakit Ödeme";
                }
                case CREDIT_CARD -> {
                    return "Kredi Kartı Ödemesi";
                }
                case BANK_TRANSFER -> {
                    return "Banka Transferi";
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }

}

