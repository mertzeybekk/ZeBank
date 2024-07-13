package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Payment;
import com.example.ZeBank.EntityLayer.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends BaseRepository<Payment>{
    @Override
    List<Payment> findAll();

    Optional<List<Payment>> findAllById(Long accountId);

    @Override
    <S extends Payment> S save(S entity);

    @Override
    void delete(Payment entity);

    @Override
    void deleteById(Long id);
}
