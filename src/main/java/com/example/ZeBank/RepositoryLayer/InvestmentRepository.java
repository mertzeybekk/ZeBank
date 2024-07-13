package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Investment;
import com.example.ZeBank.EntityLayer.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepository extends BaseRepository<Investment> {
    @Override
    List<Investment> findAll();
    List<Investment> findAllByCustomerId(Long customerId);


    @Override
    <S extends Investment> S save(S entity);

    @Override
    void delete(Investment entity);

    @Override
    void deleteById(Long id);
}
