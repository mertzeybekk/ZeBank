package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.AccountRequestDto;
import com.example.ZeBank.Dto.Request.CreditCardPayingBillsRequest;
import com.example.ZeBank.Dto.Request.CreditCardRequestDto;
import com.example.ZeBank.Dto.Response.AccountResponseDto;
import com.example.ZeBank.Dto.Response.CreditCardResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.CreditCard;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService extends GenericService<CreditCard, CreditCardRequestDto, CreditCardResponseDto> {
    CreditCardResponseDto creditCardTransaction(CreditCardPayingBillsRequest creditCardPayingBillsRequest,String invoiceNumber);
}
