package com.example.ZeBank.EntityLayer.Enum;

public enum PaymentStatus {
    PENDING("Beklemede"),
    COMPLETED("Tamamlandı"),
    FAILED("Başarısız");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
