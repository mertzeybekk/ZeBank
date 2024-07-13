package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.LoanRequestDto;
import com.example.ZeBank.Dto.Response.LoanInformationResponseDto;
import com.example.ZeBank.Dto.Response.LoanResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.LoanStatus;
import com.example.ZeBank.EntityLayer.Enum.LoanType;
import com.example.ZeBank.EntityLayer.Loan;
import com.example.ZeBank.MapperLayer.LoanMapper;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import com.example.ZeBank.RepositoryLayer.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl extends GenericServiceImpl<Loan, LoanRequestDto, LoanResponseDto> implements LoanService {
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    public LoanServiceImpl(LoanRepository loanRepository, CustomerRepository customerRepository) {
        super(loanRepository);
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<LoanResponseDto> findAll() {
        logger.info("Finding all loans");
        List<Loan> loans = loanRepository.findAll();
        logger.info("Found {} loans", loans.size());
        return LoanMapper.mapToLoanResponseDtoList(loans);
    }

    @Override
    public Optional<List<LoanResponseDto>> findById(Long id) {
        logger.info("Finding loans by id: {}", id);
        Optional<List<Loan>> loanOptional = loanRepository.findAllByCustomerId(id);

        if (loanOptional.isPresent() && !loanOptional.get().isEmpty()) {
            logger.info("Loans found with id: {}", id);
            List<LoanResponseDto> loanResponseDtos = loanOptional.get().stream()
                    .map(LoanMapper::mapToLoanResponseDto)
                    .collect(Collectors.toList());
            return Optional.of(loanResponseDtos);
        } else {
            logger.warn("Loan not found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public LoanResponseDto save(LoanRequestDto entity) {
        Customer customer = customerRepository.findById(Long.valueOf(entity.getCustomerId()))
                .orElseThrow(() -> {
                    logger.error("Customer not found with id: {}", entity.getCustomerId());
                    return new EntityNotFoundException("Customer not found with id: " + entity.getCustomerId());
                });

        List<Account> accountList = customer.getAccounts();
        Optional<List<Loan>> loanList = loanRepository.findAllByCustomerId(entity.getCustomerId());

        boolean hasActiveLoan = loanList.isPresent() && loanList.get().stream()
                .anyMatch(loan -> loan.getLoanStatus() == LoanStatus.ACTIVE);

        boolean hasPendingLoan = loanList.isPresent() && loanList.get().stream()
                .anyMatch(loan -> loan.getLoanStatus() == LoanStatus.PENDING);

        boolean hasRejectedLoan = loanList.isPresent() && loanList.get().stream()
                .anyMatch(loan -> loan.getLoanStatus() == LoanStatus.REJECTED);

        boolean hasSufficientBalance = accountList.stream()
                .anyMatch(account -> account.getBalance() >= entity.getLoanAmount());

        if (hasActiveLoan) {
            return new LoanResponseDto("Aktif bir krediniz var. Lütfen yeni bir krediye başvurmadan önce mevcut kredinizi ödeyin.");
        } else if (hasPendingLoan) {
            return new LoanResponseDto("Bekleyen bir kredi başvurunuz var. Lütfen mevcut kredi başvurunuzun onaylanmasını veya reddedilmesini bekleyin.");
        } else if (hasRejectedLoan) {
            return new LoanResponseDto("Önceki kredi başvurunuz reddedildi. Lütfen uygunluk kriterlerinizi kontrol edin ve daha sonra tekrar deneyin.");
        } else if (!hasSufficientBalance) {
            return new LoanResponseDto("Hesaplarınızda yetersiz bakiye. Krediye başvurmadan önce lütfen hesaplarınıza para yatırın.");
        } else {
            entity.setLoanStatus(String.valueOf(LoanStatus.APPROVED));
        }

        Loan loan = LoanMapper.dtoToEntity(entity, customer);
        Loan savedLoan = loanRepository.save(loan);

        logger.info("Loan saved: {}", savedLoan);
        return LoanMapper.entityToDto(savedLoan);
    }


    @Override
    public String delete(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Loan not found with id: {}", id);
                    return new EntityNotFoundException("Loan not found with id: " + id);
                });

        loanRepository.delete(loan);
        logger.info("Loan deleted: {}", loan);
        return "Loan Deleted";
    }

    @Override
    public LoanResponseDto update(Long id, LoanRequestDto entity) {
        Loan existingLoan = loanRepository.findById((id))
                .orElseThrow(() -> {
                    logger.error("Loan not found with id: {}", id);
                    return new EntityNotFoundException("Loan not found with id: " + id);
                });

        existingLoan.setLoanType(LoanType.valueOf(entity.getLoanType()));
        existingLoan.setLoanAmount(entity.getLoanAmount());
        existingLoan.setInterestRate(entity.getInterestRate());
        existingLoan.setPaymentStart(entity.getPaymentStart());
        existingLoan.setPaymentEnd(entity.getPaymentEnd());
        existingLoan.setLoanDurationMonths(entity.getLoanDurationMonths());
        existingLoan.setRemainingAmount(existingLoan.getLoanAmount() - entity.getLoanAmount());
        if (existingLoan.getLoanAmount() == 0) {
            existingLoan.setLoanStatus(LoanStatus.CLOSED);
            existingLoan.getCustomer().setCreditScore(2000);
        }

        Loan updatedLoan = loanRepository.save(existingLoan);

        logger.info("Loan updated: {}", updatedLoan);
        return LoanMapper.entityToDto(updatedLoan);
    }

    public List<LoanInformationResponseDto> getLoanInformation() {
        return new LoanMapper().mapToLoanInformation();
    }
}
