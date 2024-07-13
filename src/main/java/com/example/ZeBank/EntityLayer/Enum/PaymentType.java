package com.example.ZeBank.EntityLayer.Enum;

public enum PaymentType {
    CASH("Nakit Ödeme"),
    CREDIT_CARD("Kredi Kartı Ödemesi"),
    BANK_TRANSFER("Banka Transferi"),
    CREDIT_PAY("Kredi Ödemesi");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
