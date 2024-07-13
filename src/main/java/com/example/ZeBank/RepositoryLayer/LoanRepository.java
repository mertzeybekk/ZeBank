package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Loan;
import com.example.ZeBank.EntityLayer.Payment;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends BaseRepository<Loan> {
    @Override
    List<Loan> findAll();

    Optional<List<Loan>> findAllByCustomerId(Long loanId);


    @Override
    <S extends Loan> S save(S entity);

    @Override
    void delete(Loan entity);

    @Override
    void deleteById(Long id);

    List<Loan> findByCustomerIdAndPaymentStart(Long customerId, Date paymentStart);
}
