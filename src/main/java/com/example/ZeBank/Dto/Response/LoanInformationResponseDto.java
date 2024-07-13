package com.example.ZeBank.Dto.Response;

import com.example.ZeBank.EntityLayer.Enum.LoanType;

import java.util.List;
import java.util.Map;

public class LoanInformationResponseDto {
    private Map<String, LoanType> loanTypeMap;

    public LoanInformationResponseDto(Map<String, LoanType> loanTypeMap) {
        this.loanTypeMap = loanTypeMap;
    }
    public LoanInformationResponseDto(){

    }

    public Map<String, LoanType> getLoanTypeMap() {
        return loanTypeMap;
    }

    public void setLoanTypeMap(Map<String, LoanType> loanTypeMap) {
        this.loanTypeMap = loanTypeMap;
    }
}
