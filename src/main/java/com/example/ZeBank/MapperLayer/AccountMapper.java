package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.AccountRequestDto;
import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Response.AccountResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.AccountStatus;
import com.example.ZeBank.EntityLayer.Enum.AccountType;
import com.example.ZeBank.Message.AccountMessage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountResponseDto convertToAccountResponseDto(Account account, AccountType accountType) {
        if (Objects.nonNull(account)) {
            AccountResponseDto responseDto = new AccountResponseDto();
            responseDto.setAccountId(account.getId());
            responseDto.setAccountType(account.getAccountType().name());
            responseDto.setBalance(account.getBalance());
            responseDto.setAccountOpeningDate(account.getAccountOpeningDate().toString());
            responseDto.setAccountStatus(account.getAccountStatus().name());
            responseDto.setCustomerId(account.getCustomer().getId());
            responseDto.setAccountNumber(account.getAccountNumber());
            return responseDto;
        }
        return null;
    }

    public static AccountResponseDto convertToLocalizedAccountResponseDto(Account account) {
        if (Objects.nonNull(account)) {
            AccountResponseDto responseDto = new AccountResponseDto();
            responseDto.setAccountNumber(account.getAccountNumber());
            responseDto.setAccountId(account.getId());
            responseDto.setAccountType(convertAccountTypeToString(account.getAccountType()));
            responseDto.setAccountStatus(convertAccountStatusToString(account.getAccountStatus()));
            responseDto.setBalance(account.getBalance());
            responseDto.setAccountOpeningDate(String.valueOf(account.getAccountOpeningDate()));
            responseDto.setAccountStatus(String.valueOf(account.getAccountStatus()));
            return responseDto;
        }
        return null;
    }

    public static String convertAccountTypeToString(AccountType accountType) {
        if (Objects.nonNull(accountType)) {
            switch (accountType) {
                case SAVINGS:
                    return "Tasarruf Hesabı";
                case CURRENT:
                    return "Vadesiz Hesap";
                case DEPOSIT:
                    return "Vadeli Mevduat Hesabı";
                case CREDIT:
                    return "Kredi Hesabı";
                default:
                    throw new IllegalArgumentException("Unknown account type: " + accountType);
            }
        }
        return null;
    }

    public static String convertAccountStatusToString(AccountStatus accountStatus) {
        if (Objects.nonNull(accountStatus)) {
            switch (accountStatus) {
                case ACTIVE:
                    return "Aktif";
                case INACTIVE:
                    return "Pasif";
                case BLOCKED:
                    return "Engelli";
                default:
                    throw new IllegalArgumentException("Unknown account status: " + accountStatus);
            }
        }
        return null;
    }

    public static List<AccountResponseDto> convertToAccountResponseDtoList(List<Account> account) {
        return account.stream()
                .map(AccountMapper::convertToLocalizedAccountResponseDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static Account cloneAccount(Account sourceAccount) {
        if (Objects.nonNull(sourceAccount)) {
            Account clonedAccount = new Account();
            clonedAccount.setBalance(sourceAccount.getBalance());
            clonedAccount.setAccountType(sourceAccount.getAccountType());
            clonedAccount.setAccountNumber(sourceAccount.getAccountNumber());
            return clonedAccount;
        }
        return null;
    }


}
