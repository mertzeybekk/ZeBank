package com.example.ZeBank.Service;

import com.example.ZeBank.CustomException.ResourceNotFoundException;
import com.example.ZeBank.Dto.Request.CreditCardPayingBillsRequest;
import com.example.ZeBank.Dto.Request.CreditCardRequestDto;
import com.example.ZeBank.Dto.Request.TransactionRequestDto;
import com.example.ZeBank.Dto.Response.CreditCardResponseDto;
import com.example.ZeBank.EntityLayer.*;
import com.example.ZeBank.EntityLayer.Enum.CreditCardStatus;
import com.example.ZeBank.EntityLayer.Enum.TransactionStatus;
import com.example.ZeBank.EntityLayer.Enum.TransactionType;
import com.example.ZeBank.MapperLayer.CreditCardMapper;
import com.example.ZeBank.RepositoryLayer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreditCardServiceImpl extends GenericServiceImpl<CreditCard, CreditCardRequestDto, CreditCardResponseDto> implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final InvoiceRepository invoiceRepository;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);


    public CreditCardServiceImpl(BaseRepository<CreditCard> baseRepository,
                                 CreditCardRepository creditCardRepository,
                                 CustomerRepository customerRepository,
                                 AccountRepository accountRepository,
                                 InvoiceRepository invoiceRepository,
                                 TransactionRepository transactionRepository) {
        super(baseRepository);
        this.creditCardRepository = creditCardRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.invoiceRepository = invoiceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<CreditCardResponseDto> findAll() {
        logger.info("Fetching creditCard All");
        List<CreditCard> creditCards = creditCardRepository.findAll();
        return CreditCardMapper.mapToCreditCardList(creditCards);
    }

    @Override
    public Optional<List<CreditCardResponseDto>> findById(Long id) {
        logger.info("Fetching creditCard by id: {}", id);
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        creditCard.ifPresent(creditCard1 ->
                logger.info("CreditCard fetching. CreditCard ID: {}, Name: {}", creditCard1.getCardNumber(), creditCard1.getCardHolderName()));
        return Optional.ofNullable(Collections.singletonList(CreditCardMapper.mapToCreditCardResponse(creditCard.get())));
    }

    @Override

    public CreditCardResponseDto save(CreditCardRequestDto entity) {
        logger.info("Starting save process for CreditCardRequestDto: {}", entity);

        CreditCard creditCard = new CreditCardMapper(customerRepository, accountRepository).mapToCreditCard(entity);

        logger.info("CreditCard mapped successfully: {}", creditCard);

        CreditCard savedCreditCard = creditCardRepository.save(creditCard);

        logger.info("CreditCard saved successfully: {}", savedCreditCard);

        CreditCardResponseDto responseDto = CreditCardMapper.mapToCreditCardResponse(savedCreditCard);

        logger.info("Mapped CreditCard to CreditCardResponseDto: {}", responseDto);

        return responseDto;
    }

    @Override
    public String delete(Long id) {
        logger.info("Deleting creditCard by id: {}", id);
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        creditCard.ifPresentOrElse(customer -> {
            creditCardRepository.deleteById(id);
            logger.info("CreditCard deleted. CreditCard ID: {}, Name: {}", creditCard.get().getCardNumber(), creditCard.get().getCardHolderName());
        }, () -> {
            throw new ResourceNotFoundException("CreditCard not found with id: " + id);
        });
        return "CreditCard Deleted" + id;
    }

    @Override
    public CreditCardResponseDto update(Long Id, CreditCardRequestDto entity) {
        CreditCard card = creditCardRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("CreditCard not found with id: " + Id));
        card.setBalance(entity.getCreditCardLimit());
        card.setCreditCardStatus(CreditCardStatus.PASSIVE);
        CreditCard creditCard = creditCardRepository.save(card);

        return CreditCardMapper.mapToCreditCardResponse(creditCard);
    }


    @Override
    public CreditCardResponseDto creditCardTransaction(CreditCardPayingBillsRequest creditCardPayingBillsRequest, String invoiceNumber) {
        CreditCard creditCard = creditCardRepository.findByCardNumber(creditCardPayingBillsRequest.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Credit card not found with card number: " + creditCardPayingBillsRequest.getCardNumber()));
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with invoice number: " + invoiceNumber));
        double invoiceAmount = invoice.getTotalAmount();
        invoice.setPaid(Boolean.FALSE);
        switch (invoice.getInvoiceType()) {
            case WATER:
                return updateWaterInvoice(invoice, creditCard, invoiceAmount);
            case INTERNET:
                return updateInternetInvoice(invoice, creditCard, invoiceAmount);
            case TELEPHONE:
                return updateTelephoneInvoice(invoice, creditCard, invoiceAmount);
            case ELECTRICITY:
                return updateElectricityInvoice(invoice, creditCard, invoiceAmount);
            case NATURAL_GAS:
                return updateNaturalGasInvoice(invoice, creditCard, invoiceAmount);
            default:
                throw new ResourceNotFoundException("Unknown invoice type found for invoice number: " + invoiceNumber);
        }
    }

    private CreditCardResponseDto updateInternetInvoice(Invoice invoice, CreditCard card, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        if (paymentDate != null && invoiceDate != null && paymentDate.after(invoiceDate)) {
            double originalAmount = invoice.getTotalAmount();
            double lateFeePercentage = 0.05; // %5 gecikme ücreti
            double lateFeeAmount = originalAmount * lateFeePercentage;
            double updatedAmount = originalAmount + lateFeeAmount;
            invoice.setTotalAmount(updatedAmount);
            logger.info("Internet invoice updated due to late payment. Invoice ID: {}, Original Amount: {}, Updated Amount: {}",
                    invoice.getId(), originalAmount, updatedAmount);
        } else {
            logger.info("No late payment fee applied for internet invoice. Invoice ID: {}, Amount: {}",
                    invoice.getId(), invoice.getTotalAmount());
        }
        updateAccountBalances(card, invoiceAmount);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        performPaymentTransaction(card, invoiceAmount, paymentDate, TransactionType.INTERNET);
        return CreditCardMapper.mapToPayingBillsDto(updatedInvoice);
    }


    public CreditCardResponseDto updateElectricityInvoice(Invoice invoice, CreditCard card, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        if (paymentDate != null && invoiceDate != null && paymentDate.after(invoiceDate)) {
            double originalAmount = invoice.getTotalAmount();
            double lateFeePercentage = 0.15; // %15 gecikme ücreti
            double lateFeeAmount = originalAmount * lateFeePercentage;
            double updatedAmount = originalAmount + lateFeeAmount;
            invoice.setTotalAmount(updatedAmount);
            logger.info("Electricity invoice updated due to late payment. Invoice ID: {}, Original Amount: {}, Updated Amount: {}",
                    invoice.getId(), originalAmount, updatedAmount);
        } else {
            logger.info("No late payment fee applied for electricity invoice. Invoice ID: {}, Amount: {}",
                    invoice.getId(), invoice.getTotalAmount());
        }
        updateAccountBalances(card, invoiceAmount);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        performPaymentTransaction(card, invoiceAmount, paymentDate, TransactionType.ELECTRIC);
        return CreditCardMapper.mapToPayingBillsDto(updatedInvoice);
    }


    public CreditCardResponseDto updateNaturalGasInvoice(Invoice invoice, CreditCard card, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        if (paymentDate != null && invoiceDate != null && paymentDate.after(invoiceDate)) {
            double originalAmount = invoice.getTotalAmount();
            double lateFeePercentage = 0.55; // %55 gecikme ücreti
            double lateFeeAmount = originalAmount * lateFeePercentage;
            double updatedAmount = originalAmount + lateFeeAmount;
            invoice.setTotalAmount(updatedAmount);
            logger.info("Natural gas invoice updated due to late payment. Invoice ID: {}, Original Amount: {}, Updated Amount: {}",
                    invoice.getId(), originalAmount, updatedAmount);
        } else {
            logger.info("No late payment fee applied for natural gas invoice. Invoice ID: {}, Amount: {}",
                    invoice.getId(), invoice.getTotalAmount());
        }
        updateAccountBalances(card, invoiceAmount);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        performPaymentTransaction(card, invoiceAmount, paymentDate, TransactionType.NATURALGAS);
        return CreditCardMapper.mapToPayingBillsDto(updatedInvoice);
    }

    public CreditCardResponseDto updateTelephoneInvoice(Invoice invoice, CreditCard card, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        if (paymentDate != null && invoiceDate != null && paymentDate.after(invoiceDate)) {
            double originalAmount = invoice.getTotalAmount();
            double lateFeePercentage = 0.95; // %95 gecikme ücreti
            double lateFeeAmount = originalAmount * lateFeePercentage;
            double updatedAmount = originalAmount + lateFeeAmount;
            invoice.setTotalAmount(updatedAmount);
            logger.info("Telephone invoice updated due to late payment. Invoice ID: {}, Original Amount: {}, Updated Amount: {}",
                    invoice.getId(), originalAmount, updatedAmount);
        } else {
            logger.info("No late payment fee applied for telephone invoice. Invoice ID: {}, Amount: {}",
                    invoice.getId(), invoice.getTotalAmount());
        }
        updateAccountBalances(card, invoiceAmount);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        performPaymentTransaction(card, invoiceAmount, paymentDate, TransactionType.TELEPHONE);
        return CreditCardMapper.mapToPayingBillsDto(updatedInvoice);
    }

    public CreditCardResponseDto updateWaterInvoice(Invoice invoice, CreditCard card, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        if (paymentDate != null && invoiceDate != null && paymentDate.after(invoiceDate)) {
            double originalAmount = invoice.getTotalAmount();
            double lateFeePercentage = 0.05; // %5 gecikme ücreti
            double lateFeeAmount = originalAmount * lateFeePercentage;
            double updatedAmount = originalAmount + lateFeeAmount;
            invoice.setTotalAmount(updatedAmount);
            logger.info("Water invoice updated due to late payment. Invoice ID: {}, Original Amount: {}, Updated Amount: {}",
                    invoice.getId(), originalAmount, updatedAmount);
        } else {
            logger.info("No late payment fee applied for water invoice. Invoice ID: {}, Amount: {}",
                    invoice.getId(), invoice.getTotalAmount());
        }

        updateAccountBalances(card, invoiceAmount);

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        performPaymentTransaction(card, invoiceAmount, paymentDate, TransactionType.WATER);

        return CreditCardMapper.mapToPayingBillsDto(updatedInvoice);
    }

    private void updateAccountBalances(CreditCard card, double invoiceAmount) {
        CreditCard creditCard = creditCardRepository.findByCardNumber(card.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Credit card not found with card number: " + card.getCardNumber()));

        double currentBalance = creditCard.getBalance();
        if (currentBalance < invoiceAmount) {
            throw new ResourceNotFoundException("Insufficient balance for the transaction.");
        }
        creditCard.setBalance(currentBalance - invoiceAmount);
        creditCardRepository.save(creditCard);
    }

    private void performPaymentTransaction(CreditCard card, double invoiceAmount, Date paymentDate, TransactionType transactionType) {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAmount(invoiceAmount);
        transactionRequestDto.setTransactionStatus(String.valueOf(TransactionStatus.COMPLETED));
        transactionRequestDto.setTransactionType(String.valueOf(transactionType));


        logger.info("Performing transaction with type: {}", transactionType);

        transactionRequestDto.setAccountId(card.getId());
        Transaction transaction = mapToTransactionDto(transactionRequestDto, paymentDate);

        logger.info("Saving transaction: {}", transaction);
        transactionRepository.save(transaction);
    }

    private Transaction mapToTransactionDto(TransactionRequestDto transactionRequestDto, Date paymentDate) {
        Optional<Account> account = accountRepository.findById(transactionRequestDto.getAccountId());
        if (account.isPresent() && Objects.nonNull(transactionRequestDto)) {
            Transaction transaction = new Transaction();
            transaction.setTransactionType(TransactionType.valueOf(transactionRequestDto.getTransactionType()));
            transaction.setAccount(account.get());
            transaction.setAmount(transactionRequestDto.getAmount());
            transaction.setTransactionStatus(TransactionStatus.valueOf(transactionRequestDto.getTransactionStatus()));
            transaction.setTransactionDateTime(paymentDate);
            return transaction;
        } else {
            throw new ResourceNotFoundException("Account not found with ID: " + transactionRequestDto.getAccountId());
        }
    }
}
