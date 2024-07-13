package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Request.TransactionRequestDto;
import com.example.ZeBank.Dto.Response.TransactionResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.TransactionStatus;
import com.example.ZeBank.EntityLayer.Enum.TransactionType;
import com.example.ZeBank.EntityLayer.Transaction;
import com.example.ZeBank.RepositoryLayer.AccountRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionMapper {
    private AccountRepository accountRepository;

    public TransactionMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public TransactionMapper() {

    }

    public Transaction dtoToEntity(TransactionRequestDto transactionRequestDto) {
        Optional<Account> accountOptional = accountRepository.findById(transactionRequestDto.getAccountId());
        Account account = accountOptional.orElseThrow(() -> new EntityNotFoundException("Hesap bulunamadı."));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionStatus(TransactionStatus.valueOf(transactionRequestDto.getTransactionStatus()));
        transaction.setTransactionType(TransactionType.valueOf(transactionRequestDto.getTransactionType()));
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setFromIban(transactionRequestDto.getFromIban());
        transaction.setToIban(transactionRequestDto.getToIban());
        transaction.setTransactionDateTime(new Date());
        return transaction;
    }

    public Transaction dtoToEntity(TransactionRequestDto transactionRequestDto, List<Account> accountList) {

        Account selectedAccount = accountList.stream()
                .filter(account -> account.getId().equals((transactionRequestDto.getAccountId())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));

        double paymentAmount = transactionRequestDto.getAmount();
        if (selectedAccount.getBalance() < paymentAmount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Bakiyeden düşme
        selectedAccount.setBalance(selectedAccount.getBalance() - paymentAmount);
        accountRepository.save(selectedAccount);

        Transaction transaction = new Transaction();
        transaction.setAccount(selectedAccount);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setFromIban(transactionRequestDto.getFromIban());
        transaction.setToIban(transactionRequestDto.getToIban());
        transaction.setTransactionDateTime(new Date());
        return transaction;
    }

    public static TransactionResponseDto mapToTransactionResponseDto(Transaction transaction) {
        if (Objects.nonNull(transaction)) {
            TransactionResponseDto responseDto = new TransactionResponseDto();
            responseDto.setAccountId(transaction.getAccount().getId());
            responseDto.setTransactionDateTime(transaction.getTransactionDateTime());
            responseDto.setTransactionType(mapToTransactionType(transaction.getTransactionType()));
            responseDto.setAmount(transaction.getAmount());
            responseDto.setTransactionStatus(mapToTransactionStatus(transaction.getTransactionStatus()));
            responseDto.setFromIban(transaction.getFromIban());
            responseDto.setToIban(transaction.getToIban());
            responseDto.setId(transaction.getId());
            return responseDto;
        }
        return null;

    }

    public static List<TransactionResponseDto> mapToTransactionResponseDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionMapper::mapToTransactionResponseDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static String mapToTransactionType(TransactionType transactionType) {
        if (Objects.nonNull(transactionType)) {
            switch (transactionType) {
                case DEPOSIT -> {
                    return "Yatırım İşlemi";
                }
                case TRANSFER -> {
                    return "Para Transfer";
                }
                case WITHDRAWAL -> {
                    return "Çekim İşlemi";
                }
                case ELECTRIC -> {
                    return "Elektrik Faturası Ödeme";
                }
                case WATER -> {
                    return "Su Faturası Ödeme";
                }
                case NATURALGAS -> {
                    return "Doğal Gaz Faturası Ödeme";
                }
                case INTERNET -> {
                    return "İnternet Faturası Ödeme";
                }
                case TELEPHONE -> {
                    return "Telefon Faturası Ödeme";
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }

    public static String mapToTransactionStatus(TransactionStatus transactionStatus) {
        if (Objects.nonNull(transactionStatus)) {
            switch (transactionStatus) {
                case PENDING -> {
                    return "Beklemede";
                }
                case CANCELLED -> {
                    return "İptal Edildi";
                }
                case COMPLETED -> {
                    return "Tamamlandı";
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }
}