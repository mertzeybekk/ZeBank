package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Insurance;
import com.example.ZeBank.EntityLayer.Investment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRepository  extends BaseRepository<Insurance> {
    @Override
    List<Insurance> findAll();


    Optional<List<Insurance>> findAllById(Long accountId);

    @Override
    <S extends Insurance> S save(S entity);

    @Override
    void delete(Insurance entity);

    @Override
    void deleteById(Long id);
}
