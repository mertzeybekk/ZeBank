package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction>{
    @Override
    List<Transaction> findAll();

    Optional<List<Transaction>> findAllByAccountId(Long accountId);

    @Override
    <S extends Transaction> S save(S entity);

    @Override
    void delete(Transaction entity);

    @Override
    void deleteById(Long id);
}
