package com.example.ZeBank.EntityLayer.Enum;

public enum CreditCardStatus {
    ACTIVE("Aktif"),
    PASSIVE("pasif");
    private final String cardStatus;

    CreditCardStatus(String displayName) {
        this.cardStatus = displayName;
    }

    public String getDisplayName() {
        return cardStatus;
    }
}
