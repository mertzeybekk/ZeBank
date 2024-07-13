package com.example.ZeBank.Service;

import com.example.ZeBank.CustomException.ResourceNotFoundException;
import com.example.ZeBank.Dto.Request.AccountRequestDto;
import com.example.ZeBank.Dto.Response.AccountResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.AccountStatus;
import com.example.ZeBank.EntityLayer.Enum.AccountType;
import com.example.ZeBank.MapperLayer.AccountMapper;
import com.example.ZeBank.RepositoryLayer.AccountRepository;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, AccountRequestDto, AccountResponseDto> implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<AccountResponseDto> findAll() {
        logger.info("Finding all accounts");
        List<Account> accounts = accountRepository.findAll();

        return AccountMapper.convertToAccountResponseDtoList(accounts);
    }

    @Override
    public Optional<List<AccountResponseDto>> findById(Long id) {
        logger.info("Getting accounts by id: {}", id);
        Optional<List<Account>> accountOptional = Optional.ofNullable(accountRepository.findAllByCustomerId(id));

        if (accountOptional.isPresent()) {
            List<Account> accounts = accountOptional.get();
            List<AccountResponseDto> accountResponseDtos = accounts.stream()
                    .map(AccountMapper::convertToLocalizedAccountResponseDto)
                    .collect(Collectors.toList());
            return Optional.of(accountResponseDtos);
        } else {
            logger.info("No accounts found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public AccountResponseDto save(AccountRequestDto requestDto) {
        logger.info("Saving new account: {}", requestDto);
        Account account = new Account();
        account.setBalance(requestDto.getBalance());
        account.setAccountType(AccountType.valueOf(requestDto.getAccountType()));
        account.setAccountOpeningDate(new Date());
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountNumber(requestDto.getAccountNumber());

        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + requestDto.getCustomerId()));
        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);
        logger.info("Account saved: {}", savedAccount);
        return AccountMapper.convertToAccountResponseDto(savedAccount, AccountType.valueOf(requestDto.getAccountType()));
    }

    @Override
    public String delete(Long id) {
        logger.info("Deleting account by id: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        accountRepository.deleteById(account.getId());
        return "Deleted Account";
    }

    @Override
    public AccountResponseDto update(Long id, AccountRequestDto requestDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        account.setBalance(requestDto.getBalance());
        account.setAccountType(AccountType.valueOf(requestDto.getAccountType()));
        account.setAccountNumber(requestDto.getAccountNumber());

        Account sourceAccount = AccountMapper.cloneAccount(account);

        Account updatedAccount = accountRepository.save(sourceAccount);
        logger.info("Account updated: {}", updatedAccount);
        return AccountMapper.convertToAccountResponseDto(updatedAccount, AccountType.valueOf(requestDto.getAccountType()));
    }
}
