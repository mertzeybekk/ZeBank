package com.example.ZeBank.Service;

import com.example.ZeBank.CustomException.ResourceNotFoundException;
import com.example.ZeBank.Dto.Request.InvoiceRequestDto;
import com.example.ZeBank.Dto.Request.TransactionRequestDto;
import com.example.ZeBank.Dto.Response.InvoiceResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.TransactionStatus;
import com.example.ZeBank.EntityLayer.Enum.TransactionType;
import com.example.ZeBank.EntityLayer.Invoice;
import com.example.ZeBank.EntityLayer.Transaction;
import com.example.ZeBank.MapperLayer.InvoiceMapper;
import com.example.ZeBank.RepositoryLayer.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, InvoiceRequestDto, InvoiceResponseDto> implements InvoiceService {
    private static InvoiceServiceImpl instance;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    public InvoiceServiceImpl(BaseRepository<Invoice> baseRepository, InvoiceRepository invoiceRepository,
                              CustomerRepository customerRepository, AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        super(baseRepository);
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        instance = this;
    }

    public static InvoiceServiceImpl getInstance() {
        if (instance == null) {
            throw new IllegalStateException("InvoiceServiceImpl instance has not been initialized yet.");
        }
        return instance;
    }

    @Override
    public List<InvoiceResponseDto> findAll() {
        List<Invoice> invoices = invoiceRepository.findByPaidTrue();
        return InvoiceMapper.mapToInvoiceList(invoices);
    }

    @Override
    public Optional<List<InvoiceResponseDto>> findById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoice -> Collections.singletonList(InvoiceMapper.mapToInvoiceResponseDto(invoice)));
    }

    public Optional<List<InvoiceResponseDto>> findByInvoiceNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .map(invoice -> Collections.singletonList(InvoiceMapper.mapToInvoiceResponseDto(invoice)));
    }

    @Override
    public InvoiceResponseDto save(InvoiceRequestDto requestDto) {
        // Find the customer by ID
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + requestDto.getCustomerId()));

        // Map DTO to entity
        Invoice invoice = InvoiceMapper.mapToInvoice(requestDto, customer);

        // Save the invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Log the saved invoice
        logger.info("Saved invoice with ID: {}, Invoice Number: {}", savedInvoice.getId(), savedInvoice.getInvoiceNumber());

        // Map and return response DTO
        return InvoiceMapper.mapToInvoiceResponseDto(savedInvoice);
    }

    @Override
    public InvoiceResponseDto update(Long id, InvoiceRequestDto requestDto) {
        // Find the existing invoice by ID
//        Invoice existingInvoice = invoiceRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with ID: " + id));
//
//        // Update existing invoice with new data
//        existingInvoice.setInvoiceNumber(requestDto.getInvoiceNumber());
//        existingInvoice.setTotalAmount(requestDto.getTotalAmount());
//        existingInvoice.setInvoiceDate(requestDto.getInvoiceDate());
//        existingInvoice.setPaymentDate(new Date());
//        existingInvoice.setInvoiceType(requestDto.getInvoiceType());
//
//        // Save the updated invoice
//        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
//
//        // Log the updated invoice
//        logger.info("Updated invoice with ID: {}, Invoice Number: {}", updatedInvoice.getId(), updatedInvoice.getInvoiceNumber());
//
//        // Map and return response DTO
//        return InvoiceMapper.mapToInvoiceResponseDto(updatedInvoice);
        return null;
    }

    @Override
    public String delete(Long id) {
        // Find the existing invoice by ID
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with ID: " + id));

        // Delete the invoice
        invoiceRepository.deleteById(id);

        // Log the deleted invoice
        logger.info("Deleted invoice with ID: {}, Invoice Number: {}", invoice.getId(), invoice.getInvoiceNumber());

        return "Invoice deleted successfully";
    }

    public InvoiceResponseDto updateInvoice(String invoiceNumber) {
        // Find the invoice by invoice number
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with invoice number: " + invoiceNumber));
        double invoiceAmount = invoice.getTotalAmount();
        invoice.setPaid(Boolean.FALSE);
        List<Account> accounts = invoice.getCustomer().getAccounts();
        // Update invoice based on its type
        switch (invoice.getInvoiceType()) {
            case WATER:
                // Handle water invoice update logic
                // Example: updateWaterInvoice(invoice);
                return updateWaterInvoice(invoice, accounts, invoiceAmount);
            case INTERNET:
                // Handle internet invoice update logic
                // Example: updateInternetInvoice(invoice, accounts, invoiceAmount);
                return updateInternetInvoice(invoice, accounts, invoiceAmount);
            case TELEPHONE:
                // Handle telephone invoice update logic
                // Example: updateTelephoneInvoice(invoice);
                return updateTelephoneInvoice(invoice, accounts, invoiceAmount);
            case ELECTRICITY:
                // Handle electricity invoice update logic
                // Example: updateElectricityInvoice(invoice, accounts, invoiceAmount);
                return updateElectricityInvoice(invoice, accounts, invoiceAmount);
            case NATURAL_GAS:
                // Handle natural gas invoice update logic
                // Example: updateNaturalGasInvoice(invoice);
                return updateNaturalGasInvoice(invoice, accounts, invoiceAmount);
            default:
                throw new ResourceNotFoundException("Unknown invoice type found for invoice number: " + invoiceNumber);
        }
    }

    private InvoiceResponseDto updateInternetInvoice(Invoice invoice, List<Account> accounts, double invoiceAmount) {
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
        // Update account balances
        updateAccountBalances(accounts, invoiceAmount);
        // Save the updated invoice
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        // Perform transaction for the payment
        performPaymentTransaction(accounts, invoiceAmount, paymentDate, TransactionType.INTERNET);
        // Map and return response DTO
        return InvoiceMapper.mapToInvoiceResponseDto(updatedInvoice);
    }

    private InvoiceResponseDto updateElectricityInvoice(Invoice invoice, List<Account> accounts, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

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

        // Update account balances
        updateAccountBalances(accounts, invoiceAmount);

        // Save the updated invoice
        Invoice updatedInvoice = invoiceRepository.save(invoice);

        // Perform transaction for the payment
        performPaymentTransaction(accounts, invoiceAmount, paymentDate, TransactionType.ELECTRIC);

        // Map and return response DTO
        return InvoiceMapper.mapToInvoiceResponseDto(updatedInvoice);
    }

    private InvoiceResponseDto updateNaturalGasInvoice(Invoice invoice, List<Account> accounts, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

        if (paymentDate != null && invoiceDate != null && paymentDate.after(invoiceDate)) {
            double originalAmount = invoice.getTotalAmount();
            double lateFeePercentage = 0.55; // %5 gecikme ücreti
            double lateFeeAmount = originalAmount * lateFeePercentage;
            double updatedAmount = originalAmount + lateFeeAmount;
            invoice.setTotalAmount(updatedAmount);
            logger.info("Natural gas invoice updated due to late payment. Invoice ID: {}, Original Amount: {}, Updated Amount: {}",
                    invoice.getId(), originalAmount, updatedAmount);
        } else {
            logger.info("No late payment fee applied for natural gas invoice. Invoice ID: {}, Amount: {}",
                    invoice.getId(), invoice.getTotalAmount());
        }

        // Update account balances
        updateAccountBalances(accounts, invoiceAmount);

        // Save the updated invoice
        Invoice updatedInvoice = invoiceRepository.save(invoice);

        // Perform transaction for the payment
        performPaymentTransaction(accounts, invoiceAmount, paymentDate, TransactionType.NATURALGAS);

        // Map and return response DTO
        return InvoiceMapper.mapToInvoiceResponseDto(updatedInvoice);
    }

    private InvoiceResponseDto updateTelephoneInvoice(Invoice invoice, List<Account> accounts, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

        if (paymentDate != null && invoiceDate != null && paymentDate.after(invoiceDate)) {
            double originalAmount = invoice.getTotalAmount();
            double lateFeePercentage = 0.95; // %5 gecikme ücreti
            double lateFeeAmount = originalAmount * lateFeePercentage;
            double updatedAmount = originalAmount + lateFeeAmount;
            invoice.setTotalAmount(updatedAmount);
            logger.info("Telephone invoice updated due to late payment. Invoice ID: {}, Original Amount: {}, Updated Amount: {}",
                    invoice.getId(), originalAmount, updatedAmount);
        } else {
            logger.info("No late payment fee applied for telephone invoice. Invoice ID: {}, Amount: {}",
                    invoice.getId(), invoice.getTotalAmount());
        }

        // Update account balances
        updateAccountBalances(accounts, invoiceAmount);

        // Save the updated invoice
        Invoice updatedInvoice = invoiceRepository.save(invoice);

        // Perform transaction for the payment
        performPaymentTransaction(accounts, invoiceAmount, paymentDate, TransactionType.TELEPHONE);

        // Map and return response DTO
        return InvoiceMapper.mapToInvoiceResponseDto(updatedInvoice);
    }

    private InvoiceResponseDto updateWaterInvoice(Invoice invoice, List<Account> accounts, double invoiceAmount) {
        Date paymentDate = invoice.getPaymentDate();
        Date invoiceDate = invoice.getInvoiceDate();
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

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

        // Update account balances
        updateAccountBalances(accounts, invoiceAmount);

        // Save the updated invoice
        Invoice updatedInvoice = invoiceRepository.save(invoice);

        // Perform transaction for the payment
        performPaymentTransaction(accounts, invoiceAmount, paymentDate, TransactionType.WATER);

        // Map and return response DTO
        return InvoiceMapper.mapToInvoiceResponseDto(updatedInvoice);
    }

    private void updateAccountBalances(List<Account> accounts, double invoiceAmount) {
        accounts.forEach(account -> {
            double currentBalance = account.getBalance();
            if (currentBalance >= invoiceAmount) {
                account.setBalance(currentBalance - invoiceAmount);
                // Hesabın güncellenmiş bakiyesini kaydetmek gerekirse:
                accountRepository.save(account);
            } else {
                // Hesapta yeterli bakiye yoksa burada bir hata işlemi veya loglama yapılabilir
                throw new IllegalStateException("Account balance is insufficient to pay the invoice.");
            }
        });
    }

    private void performPaymentTransaction(List<Account> accounts, double invoiceAmount, Date paymentDate, TransactionType transactionType) {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAmount(invoiceAmount);
        transactionRequestDto.setTransactionStatus(String.valueOf(TransactionStatus.COMPLETED));
        transactionRequestDto.setTransactionType(String.valueOf(transactionType));

        // Log transactionType
        logger.info("Performing transaction with type: {}", transactionType);

        accounts.forEach(account -> {
            transactionRequestDto.setAccountId(account.getId());
            Transaction transaction = mapToTransactionDto(transactionRequestDto, paymentDate);
            // Log transaction before saving
            logger.info("Saving transaction: {}", transaction);
            transactionRepository.save(transaction);
        });
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
