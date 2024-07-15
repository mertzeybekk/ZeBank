package com.example.ZeBank.Service;


import com.example.ZeBank.Dto.Request.SmsTokenRequest;
import com.example.ZeBank.Dto.Response.SmsTokenResponse;
import com.example.ZeBank.EntityLayer.SmsToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SmsTokenService extends GenericService<SmsToken, SmsTokenRequest, SmsTokenResponse> {
    Optional<SmsToken> findByUserToken(String username);
}
