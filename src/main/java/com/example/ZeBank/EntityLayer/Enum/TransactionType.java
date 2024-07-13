package com.example.ZeBank.EntityLayer.Enum;

public enum TransactionType {
    DEPOSIT("Yatırım"),
    WITHDRAWAL("Çekim"),
    TRANSFER("Transfer"),
    ELECTRIC("Elektrik Faturası Ödeme"),
    WATER("Su Faturası Ödeme"),
    NATURALGAS("Doğal Gaz Faturası Ödeme"),
    INTERNET("İnternet Faturası Ödeme"),
    TELEPHONE("Telefon Faturası Ödeme");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
