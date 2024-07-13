package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.CreditCard;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends BaseRepository<CreditCard> {
    Optional<CreditCard> findByCardNumber(String cardNumber);
}
