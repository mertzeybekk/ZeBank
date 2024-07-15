package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.SmsToken;
import com.twilio.twiml.voice.Sms;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SmsTokenRepository extends BaseRepository<SmsToken> {
    Optional<SmsToken> findByUsername(String username);
}
