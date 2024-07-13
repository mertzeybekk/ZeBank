package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.AccountRequestDto;
import com.example.ZeBank.Dto.Response.AccountResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.Service.AccountService;
import com.example.ZeBank.Service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/accounts")
public class AccountController extends GenericControllerImpl<Account, AccountRequestDto, AccountResponseDto> {
    private AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(GenericService<Account, AccountRequestDto, AccountResponseDto> genericService, AccountService accountService) {
        super(genericService);
        this.accountService = accountService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAll() {
        logger.info("Fetching all accounts");
        List<AccountResponseDto> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<AccountResponseDto>> getById(@PathVariable Long id) {
        logger.info("Fetching account by ID: {}", id);
        Optional<List<AccountResponseDto>> account = accountService.findById(id);
        return ResponseEntity.ok(account.get());
    }

    @Override
    @PostMapping("/createAccount")
    public ResponseEntity<AccountResponseDto> create(@RequestBody AccountRequestDto entity) {
        logger.info("Creating a new account");
        AccountResponseDto response = accountService.save(entity);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> update(@PathVariable Long id, @RequestBody AccountRequestDto entity) {
        logger.info("Updating account with ID: {}", id);
        AccountResponseDto account = accountService.update(id, entity);
        return ResponseEntity.ok(account);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting account with ID: {}", id);
        accountService.delete(id);
        return ResponseEntity.ok("Account with ID " + id + " deleted successfully");
    }

}
