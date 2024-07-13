package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.AccountRequestDto;
import com.example.ZeBank.Dto.Response.AccountResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends GenericService<Account, AccountRequestDto, AccountResponseDto> {
}
