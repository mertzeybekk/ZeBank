package com.example.ZeBank.EntityLayer.Enum;

public enum InsuranceType {
    SAGLIK("Sağlık Sigortası"),
    HAYAT("Hayat Sigortası"),
    ARAC("Araç Sigortası"),
    CEPTELEFON("Cep Telefon");

    private final String label;

    InsuranceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
