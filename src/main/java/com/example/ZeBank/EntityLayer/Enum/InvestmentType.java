package com.example.ZeBank.EntityLayer.Enum;

public enum InvestmentType {
    STOCKS("Hisse-Senetleri"),
    BONDS("Tahvil-Ve-Bono"),
    REAL_ESTATE("Gayrimenkul"),
    COMMODITIES("Emita"),
    MUTUAL_FUNDS("Yatırım-Fonları");


    private final String displayName;

    InvestmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
