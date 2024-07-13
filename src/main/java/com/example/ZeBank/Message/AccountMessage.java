package com.example.ZeBank.Message;

import com.example.ZeBank.EntityLayer.Enum.AccountType;
import com.example.ZeBank.EntityLayer.Enum.CustomerType;

public class AccountMessage {
    public static final String ACCOUNT_CREATED = "Hesap oluşturuldu.";
    public static final String ACCOUNT_UPDATED = "Hesap bilgileri güncellendi.";
    public static final String ACCOUNT_DELETED = "Hesap silindi.";

    public static String accountMessage(String accountNumber, AccountType accountType) {
        String message = "";

        switch (accountType) {
            case SAVINGS:
                message = ACCOUNT_CREATED;
                break;
            case CURRENT:
                message = ACCOUNT_UPDATED;
                break;
            case DEPOSIT:
                message = ACCOUNT_DELETED;
                break;
            case CREDIT:
                message = "Kredi Hesabı mesajı"; // Örnek olarak ekledim, bu kısmı doldurabilirsiniz.
                break;
        }

        return String.format("%s Hesap No: %s", message, accountNumber);
    }
}