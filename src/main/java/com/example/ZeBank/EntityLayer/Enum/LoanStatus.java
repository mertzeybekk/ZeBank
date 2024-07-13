package com.example.ZeBank.EntityLayer.Enum;

public enum LoanStatus {
    PENDING("BEKLEMEDE"),
    APPROVED("ONAYLANMIŞ"),
    REJECTED("REDDEDİLMİŞ"),
    ACTIVE("AKTİF"),
    CLOSED("KAPANMIŞ"),
    NOTPAID("ODENMEMIS"),
    PAID("ODENMIS");

    private final String loanStatus;

    LoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public static String mapToLoanStatus(LoanStatus loanStatus) {
        return loanStatus != null ? loanStatus.getLoanStatus() : null;
    }
    public static LoanStatus mapToLoanStatusRequest(String loanStatus) {
        return loanStatus != null ? LoanStatus.valueOf(loanStatus): null;
    }
}

