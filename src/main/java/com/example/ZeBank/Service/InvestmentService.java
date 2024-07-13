package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.InvestmentRequestDto;
import com.example.ZeBank.Dto.Response.InvestmentResponseDto;
import com.example.ZeBank.EntityLayer.Investment;
import org.springframework.stereotype.Service;
@Service

public interface InvestmentService extends GenericService<Investment, InvestmentRequestDto, InvestmentResponseDto> {
}
