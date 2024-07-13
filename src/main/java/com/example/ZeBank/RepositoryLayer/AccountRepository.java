package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Insurance;
import com.example.ZeBank.EntityLayer.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    @Override
    List<Account> findAll();

    List<Account> findAllByCustomerId(Long customerId);

    @Override
    <S extends Account> S save(S entity);

    @Override
    void delete(Account entity);

    @Override
    void deleteById(Long id);

    Optional<Account> findByAccountNumber(String accountNumber);
}
