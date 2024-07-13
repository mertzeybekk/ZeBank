package com.example.ZeBank.EntityLayer.Enum;

public enum TransactionStatus {
    PENDING("Beklemede"),
    COMPLETED("Tamamlandı"),
    CANCELLED("İptal Edildi");

    private final String status;

    TransactionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
