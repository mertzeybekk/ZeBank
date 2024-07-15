package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.LoanRequestDto;
import com.example.ZeBank.Dto.Response.LoanInformationResponseDto;
import com.example.ZeBank.Dto.Response.LoanResponseDto;
import com.example.ZeBank.EntityLayer.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanService extends GenericService<Loan, LoanRequestDto, LoanResponseDto> {
    List<LoanInformationResponseDto>getLoanInformation();
    ResponseEntity<byte[]> createApprovalMessagePdf(LoanRequestDto savedLoan);
}
