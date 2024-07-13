package com.example.ZeBank.EntityLayer.Enum;


import java.util.HashMap;
import java.util.Map;

public enum LoanType {
    PERSONAL_LOAN("Bireysel Kredi"),
    HOME_LOAN("Konut Kredisi"),
    AUTO_LOAN("Taşıt Kredisi"),
    EDUCATION_LOAN("Eğitim Kredisi"),
    BUSINESS_LOAN("İşletme Kredisi"),
    PAYDAY_LOAN("Günlük Kredi"),
    MORTGAGE_LOAN("İpotekli Kredi"),
    CREDIT_CARD_LOAN("Kredi Kartı Kredisi"),
    DEBT_CONSOLIDATION_LOAN("Borç Birleştirme Kredisi"),
    BRIDGE_LOAN("Köprü Kredisi"),
    AGRICULTURAL_LOAN("Tarım Kredisi");

    private final String loanTypeName;

    LoanType(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public static String mapToLoanType(LoanType loanType) {
        return loanType != null ? loanType.getLoanTypeName() : null;
    }

    static final Map<String, LoanType> ENUM_MAP = new HashMap<>();

    static {
        for (LoanType loanType : LoanType.values()) {
            ENUM_MAP.put(loanType.getLoanTypeName(), loanType);
        }
    }

    public static Map<String, LoanType> map() {
        return ENUM_MAP;
    }
}

