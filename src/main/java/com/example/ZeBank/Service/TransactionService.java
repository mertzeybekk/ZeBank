package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.TransactionRequestDto;
import com.example.ZeBank.Dto.Response.TransactionResponseDto;
import com.example.ZeBank.EntityLayer.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService extends GenericService<Transaction, TransactionRequestDto, TransactionResponseDto>{
}
