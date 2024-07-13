package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.PaymentRequestDto;
import com.example.ZeBank.Dto.Response.PaymentResponseDto;
import com.example.ZeBank.EntityLayer.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService extends GenericService<Payment, PaymentRequestDto, PaymentResponseDto> {
    // PaymentService'ye özgü metotlar buraya eklenebilir
}