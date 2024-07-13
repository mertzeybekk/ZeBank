package com.example.ZeBank.EntityLayer.Enum;

public enum InvoiceType {
    ELECTRICITY("Elektrik Faturası"),
    TELEPHONE("Telefon Faturası"),
    NATURAL_GAS("Dağal Gaz Faturası"),
    WATER("Su Faturası"),
    INTERNET("İnternet Faturası");
    private final String displayName;

    InvoiceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
