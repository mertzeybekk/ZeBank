package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.TransactionRequestDto;
import com.example.ZeBank.Dto.Response.TransactionResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Enum.TransactionStatus;
import com.example.ZeBank.EntityLayer.Enum.TransactionType;
import com.example.ZeBank.EntityLayer.Transaction;
import com.example.ZeBank.MapperLayer.TransactionMapper;
import com.example.ZeBank.RepositoryLayer.AccountRepository;
import com.example.ZeBank.RepositoryLayer.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl extends GenericServiceImpl<Transaction, TransactionRequestDto, TransactionResponseDto> implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionResponseDto> findAll() {
        logger.info("Finding all transactions");
        List<Transaction> transactions = transactionRepository.findAll();
        logger.info("Found {} transactions", transactions.size());
        return TransactionMapper.mapToTransactionResponseDtoList(transactions);
    }
    @Override
    public Optional<List<TransactionResponseDto>> findById(Long id) {
        logger.info("Finding transaction by id: {}", id);
        Optional<List<Transaction>> transactionOptional = transactionRepository.findAllByAccountId(id);

        if (transactionOptional.isPresent()) {
            List<Transaction> transactions = transactionOptional.get();
            transactions.forEach(transaction -> logger.info("Transaction found. Transaction ID: {}", transaction.getId()));
            List<TransactionResponseDto> transactionResponseDtos = transactions.stream()
                    .map(TransactionMapper::mapToTransactionResponseDto)
                    .collect(Collectors.toList());
            return Optional.of(transactionResponseDtos);
        } else {
            logger.info("Transaction not found with id: {}", id);
            return Optional.empty();
        }
    }


    @Override
    public TransactionResponseDto save(TransactionRequestDto entity) {
        // Gönderici hesabı bul
        Optional<Account> fromAccountOptional = accountRepository.findByAccountNumber(entity.getFromIban());
        if (fromAccountOptional.isEmpty()) {
            throw new EntityNotFoundException("Gönderici hesap bulunamadı.");
        }
        Account fromAccount = fromAccountOptional.get();

        // Alıcı hesabı bul
        Optional<Account> toAccountOptional = accountRepository.findByAccountNumber(entity.getToIban());
        if (toAccountOptional.isEmpty()) {
            throw new EntityNotFoundException("Alıcı hesap bulunamadı.");
        }
        Account toAccount = toAccountOptional.get();

        // Transfer miktarını kontrol et
        if (fromAccount.getBalance() < entity.getAmount()) {
            throw new IllegalArgumentException("Gönderici hesapta yeterli bakiye yok.");
        }

        // Transfer işlemini gerçekleştir
        fromAccount.setBalance(fromAccount.getBalance() - entity.getAmount());
        toAccount.setBalance(toAccount.getBalance() + entity.getAmount());
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Transaction nesnesini oluştur
        Transaction transaction = new TransactionMapper(accountRepository).dtoToEntity(entity);
        transaction.setTransactionDateTime(new Date());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);

        // Transaction'ı kaydet
        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction saved: {}", savedTransaction);

        // TransactionResponseDto oluştur ve geri dön
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setId(savedTransaction.getId());
        responseDto.setAccountId(fromAccount.getId());
        responseDto.setTransactionType(savedTransaction.getTransactionType().toString());
        responseDto.setAmount(savedTransaction.getAmount());
        responseDto.setTransactionDateTime(savedTransaction.getTransactionDateTime());
        responseDto.setTransactionStatus(String.valueOf(savedTransaction.getTransactionStatus()));
        responseDto.setToIban(toAccount.getAccountNumber());
        responseDto.setFromIban(fromAccount.getAccountNumber());

        return responseDto;
    }


    @Override
    public String delete(Long id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new EntityNotFoundException("Transaction not found with id: " + id);
        }
        Transaction transaction = transactionOptional.get();
        transactionRepository.delete(transaction);
        logger.info("Transaction deleted: {}", transaction);
        return "Transaction deleted";
    }

    @Override
    public TransactionResponseDto update(Long id, TransactionRequestDto entity) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction not found with id: " + id);
        }

        Transaction existingTransaction = optionalTransaction.get();
        existingTransaction.setAmount(entity.getAmount());
        existingTransaction.setFromIban(entity.getFromIban());
        existingTransaction.setToIban(entity.getToIban());
        existingTransaction.setTransactionType(TransactionType.valueOf(entity.getTransactionType()));
        existingTransaction.setTransactionStatus(TransactionStatus.valueOf(entity.getTransactionStatus()));
        existingTransaction.setTransactionDateTime(new Date());

        logger.info("Transaction updated: {}", existingTransaction);
        transactionRepository.save(existingTransaction);

        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setId(existingTransaction.getId());
        responseDto.setAccountId(existingTransaction.getAccount().getId());
        responseDto.setTransactionType(existingTransaction.getTransactionType().toString());
        responseDto.setAmount(existingTransaction.getAmount());
        responseDto.setTransactionDateTime(existingTransaction.getTransactionDateTime());
        responseDto.setTransactionStatus(existingTransaction.getTransactionStatus().toString());
        responseDto.setToIban(existingTransaction.getToIban());
        responseDto.setFromIban(existingTransaction.getFromIban());

        return responseDto;
    }

}
