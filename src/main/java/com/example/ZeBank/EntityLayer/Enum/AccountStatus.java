package com.example.ZeBank.EntityLayer.Enum;

public enum AccountStatus {
    ACTIVE("Aktif"),
    INACTIVE("Pasif"),
    BLOCKED("Engelli");

    private final String status;

    AccountStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
