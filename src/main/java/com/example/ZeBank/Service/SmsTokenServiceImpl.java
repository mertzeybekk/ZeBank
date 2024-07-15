package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.AccountRequestDto;
import com.example.ZeBank.Dto.Request.SmsTokenRequest;
import com.example.ZeBank.Dto.Response.AccountResponseDto;
import com.example.ZeBank.Dto.Response.SmsTokenResponse;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.SmsToken;
import com.example.ZeBank.RepositoryLayer.BaseRepository;
import com.example.ZeBank.RepositoryLayer.SmsTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SmsTokenServiceImpl extends GenericServiceImpl<SmsToken, SmsTokenRequest, SmsTokenResponse> implements SmsTokenService {
    private final SmsTokenRepository smsTokenRepository;

    public SmsTokenServiceImpl(BaseRepository<SmsToken> baseRepository, SmsTokenRepository smsTokenRepository) {
        super(baseRepository);
        this.smsTokenRepository = smsTokenRepository;
    }

    @Override
    public List<SmsTokenResponse> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<List<SmsTokenResponse>> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public SmsTokenResponse save(SmsTokenRequest entity) {
        SmsToken smsToken = new SmsToken();
        smsToken.setUsername(entity.getUsername());
        smsToken.setCode(entity.getCode());
        SmsToken token = smsTokenRepository.save(smsToken);
        return new SmsTokenResponse();
    }

    @Override
    public String delete(Long id) {
        return super.delete(id);
    }

    @Override
    public SmsTokenResponse update(Long Id, SmsTokenRequest entity) {
        return super.update(Id, entity);
    }

    @Override
    public Optional<SmsToken> findByUserToken(String username) {
        return Optional.empty();
    }
}
