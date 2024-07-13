package com.example.ZeBank.EntityLayer.Enum;

public enum AccountType {
    SAVINGS("Tasarruf Hesabı"),
    CURRENT("Vadesiz Hesap"),
    DEPOSIT("Vadeli Mevduat Hesabı"),
    CREDIT("Kredi Hesabı");

    private final String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
