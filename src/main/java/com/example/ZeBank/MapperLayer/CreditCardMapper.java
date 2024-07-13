package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.CreditCardRequestDto;
import com.example.ZeBank.Dto.Response.CreditCardResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.CreditCard;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.AccountType;
import com.example.ZeBank.EntityLayer.Enum.CreditCardStatus;
import com.example.ZeBank.EntityLayer.Invoice;
import com.example.ZeBank.RepositoryLayer.AccountRepository;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CreditCardMapper {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CreditCardMapper(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public static CreditCardResponseDto mapToCreditCardResponse(CreditCard save) {
        if (Objects.nonNull(save)) {
            CreditCardResponseDto creditCardResponseDto = new CreditCardResponseDto();
            creditCardResponseDto.setCardNumber(save.getCardNumber());
            creditCardResponseDto.setCardHolderName(save.getCardHolderName());
            creditCardResponseDto.setBalance(save.getBalance());
            creditCardResponseDto.setExpirationDate(save.getExpirationDate());
            return creditCardResponseDto;
        }
        return null;
    }

    public static List<CreditCardResponseDto> mapToCreditCardList(List<CreditCard> creditCards) {
        return creditCards.stream()
                .map(CreditCardMapper::mapToCreditCardResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public CreditCard mapToCreditCard(CreditCardRequestDto requestDto) {
        if (Objects.isNull(requestDto)) {
            throw new IllegalArgumentException("CreditCardRequestDto cannot be null");
        }

        Optional<Customer> customerOpt = customerRepository.findById(Long.valueOf(requestDto.getCustomerId()));
        if (!customerOpt.isPresent()) {
            throw new EntityNotFoundException("Customer not found with ID: " + requestDto.getCustomerId());
        }

        Customer customer = customerOpt.get();
        List<Account> accounts = customer.getAccounts();
        double totalBalance = accounts.stream()
                .filter(account -> account.getAccountType() == AccountType.CURRENT || account.getAccountType() == AccountType.SAVINGS)
                .mapToDouble(Account::getBalance)
                .sum();

        if (totalBalance <= requestDto.getCreditCardLimit()) {
            throw new IllegalArgumentException("Customer's account balance does not meet the required limit.");
        }
        CreditCard creditCard = new CreditCard();
        creditCard.setCustomer(customer);
        creditCard.setCardNumber(generateRandomCardNumber());
        creditCard.setCardHolderName(customer.getName());
        creditCard.setExpirationDate(getDate());
        creditCard.setBalance((totalBalance * 4));
        creditCard.setCreditCardStatus(CreditCardStatus.ACTIVE);

        return creditCard;
    }

    private static Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 5); // to get previous year add -5
        Date nextYear = cal.getTime();
        return nextYear;
    }

    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));  // Generate a random digit between 0-9
        }
        return cardNumber.toString();
    }

    public static CreditCardResponseDto mapToPayingBillsDto(Invoice invoice) {
        if (Objects.nonNull(invoice)) {
            CreditCardResponseDto creditCardResponseDto = new CreditCardResponseDto();
            creditCardResponseDto.setInformationMessage("Invoice number " + invoice.getInvoiceNumber() + " has been paid successfully.");
            return creditCardResponseDto;
        }
        return null;
    }

}
