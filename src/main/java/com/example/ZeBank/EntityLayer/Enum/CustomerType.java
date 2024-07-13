package com.example.ZeBank.EntityLayer.Enum;

public enum CustomerType {
    CREATED("Müşteri Oluşturuldu"),

    DELETED("Müşteri Silindi"),

    UPDATING("Müşteri Bilgileri Güncellendi");


    private final String type;

    CustomerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
