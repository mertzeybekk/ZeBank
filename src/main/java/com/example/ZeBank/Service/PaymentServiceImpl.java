package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.PaymentRequestDto;
import com.example.ZeBank.Dto.Request.TransactionRequestDto;
import com.example.ZeBank.Dto.Response.PaymentResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.*;
import com.example.ZeBank.EntityLayer.Loan;
import com.example.ZeBank.EntityLayer.Payment;
import com.example.ZeBank.MapperLayer.PaymentMapper;
import com.example.ZeBank.MapperLayer.TransactionMapper;
import com.example.ZeBank.RepositoryLayer.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl extends GenericServiceImpl<Payment, PaymentRequestDto, PaymentResponseDto> implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    public PaymentServiceImpl(PaymentRepository paymentRepository, CustomerRepository customerRepository,
                              AccountRepository accountRepository, TransactionRepository transactionRepository,
                              LoanRepository loanRepository) {
        super(paymentRepository);
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<PaymentResponseDto> findAll() {
        logger.info("Finding all payments");
        List<Payment> payments = paymentRepository.findAll();
        logger.info("Found {} payments", payments.size());
        return new PaymentMapper(customerRepository).mapToPaymentResponseDtoList(payments);
    }

    @Override
    public Optional<List<PaymentResponseDto>> findById(Long id) {
        logger.info("Finding payment by id: {}", id);
        Optional<List<Payment>> paymentOptional = paymentRepository.findAllById(id);

        if (paymentOptional.isPresent()) {
            List<Payment> payments = paymentOptional.get();
            List<PaymentResponseDto> paymentResponseDtos = payments.stream()
                    .map(payment -> new PaymentMapper(customerRepository).mapToPaymentResponseDto(payment))
                    .collect(Collectors.toList());
            return Optional.of(paymentResponseDtos);
        } else {
            logger.info("No payments found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public PaymentResponseDto save(PaymentRequestDto entity) {
        logger.info("Saving payment");

        // Müşteriyi bulma
        Optional<Customer> customer = customerRepository.findById(Long.valueOf(entity.getCustomerId()));
        if (!customer.isPresent()) {
            throw new RuntimeException("Customer not found");
        }

        // Hesabı bulma ve bakiye kontrolü
        List<Account> accountList = customer.get().getAccounts();
        Account selectedAccount = accountList.stream()
                .filter(account -> account.getId().equals(Long.valueOf(entity.getAccountId())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAccountId(selectedAccount.getId());
        transactionRequestDto.setAmount(entity.getAmount());
        transactionRequestDto.setTransactionStatus(TransactionStatus.COMPLETED.getStatus());
        transactionRequestDto.setTransactionType(TransactionType.DEPOSIT.getType());
        transactionRepository.save(new TransactionMapper(accountRepository).dtoToEntity(transactionRequestDto, accountList));

        // Ödeme işlemini kaydetme
        Payment payment = new PaymentMapper(customerRepository).dtoToEntity(entity);
        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment saved: {}", savedPayment);
        if(true){
            // kafkad aracılığıyla müşterinin gmail hesabına otomatik olarak mesaj at şu kredininiz ödemesi gerçekleşti diye
        }

        return PaymentMapper.mapToPaymentResponseDto(savedPayment);
    }


    @Override
    public String delete(Long id) {
        logger.info("Deleting payment with id: {}", id);
        paymentRepository.deleteById(id);
        return "Payment deleted successfully";
    }

    @Override
    public PaymentResponseDto update(Long id, PaymentRequestDto entity) {
        logger.info("Updating payment with id: {}", id);
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty()) {
            throw new EntityNotFoundException("Payment not found with id: " + id);
        }
        Payment payment = paymentOptional.get();
        payment.setPaymentType(PaymentType.valueOf(entity.getPaymentType()));
        payment.setAmount(entity.getAmount());
        payment.setPaymentDateTime(entity.getPaymentDateTime());
        Payment updatedPayment = paymentRepository.save(payment);
        logger.info("Payment updated: {}", updatedPayment);
        return PaymentMapper.mapToPaymentResponseDto(updatedPayment);
    }
}
