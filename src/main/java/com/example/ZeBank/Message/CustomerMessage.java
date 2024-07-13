package com.example.ZeBank.Message;

import com.example.ZeBank.EntityLayer.Enum.CustomerType;

import java.util.Date;

public class CustomerMessage {
    public static final String CUSTOMER_CREATED = "Müşteri oluşturuldu.";
    public static final String CUSTOMER_UPDATED = "Müşteri bilgileri güncellendi.";
    public static final String CUSTOMER_DELETED = "Müşteri silindi.";

    public static String customerMessage(String customerNumber, CustomerType customerType) {
        String message = "";

        switch (customerType) {
            case CREATED:
                message = CUSTOMER_CREATED;
                break;
            case UPDATING:
                message = CUSTOMER_UPDATED;
                break;
            case DELETED:
                message = CUSTOMER_DELETED;
                break;
        }

        return String.format("%s Müşteri No: %s", message, customerNumber);
    }
}


