package com.example.ZeBank.Util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsSendUtil {
    @Value("${twilio.account.sid}")
    private static String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private static  String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private static String TWILIO_PHONE_NUMBER;

    public static void sendSms(String loanStatusMessage) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String toPhoneNumber = "+905393909280";
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                loanStatusMessage
        ).create();
    }
}
