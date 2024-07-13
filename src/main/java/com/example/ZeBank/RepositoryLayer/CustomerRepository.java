package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer> {
    Optional<Customer> findByName(String username);
    Optional<List<Customer>> findAllById(Long id);

}