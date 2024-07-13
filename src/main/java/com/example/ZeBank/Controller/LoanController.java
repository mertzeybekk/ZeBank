package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.LoanRequestDto;
import com.example.ZeBank.Dto.Response.LoanInformationResponseDto;
import com.example.ZeBank.Dto.Response.LoanResponseDto;
import com.example.ZeBank.EntityLayer.Loan;
import com.example.ZeBank.Service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController extends GenericControllerImpl<Loan, LoanRequestDto, LoanResponseDto> {
    private final LoanService loanService;
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    public LoanController(LoanService loanService) {
        super(loanService);
        this.loanService = loanService;
    }

    @Override
    @GetMapping("/getAllLoan")
    public ResponseEntity<List<LoanResponseDto>> getAll() {
        logger.info("Fetching all loans");
        List<LoanResponseDto> loans = loanService.findAll();
        logger.info("Found {} loans", loans.size());
        return ResponseEntity.ok(loans);
    }

    @Override
    @GetMapping("/getByIdLoan/{id}")
    public ResponseEntity<List<LoanResponseDto>> getById(@PathVariable Long id) {
        logger.info("Fetching loan by id: {}", id);
        Optional<List<LoanResponseDto>> response = loanService.findById(id);
        logger.info("Loan fetched by id: {}", id);
        return ResponseEntity.ok(response.get());
    }

    @Override
    @PostMapping
    public ResponseEntity<LoanResponseDto> create(@RequestBody LoanRequestDto entity) {
        logger.info("Applying for a new loan");
        LoanResponseDto response = loanService.save(entity);
        logger.info("New loan application successful");
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/getUpdateByIdLoan/{id}")
    public ResponseEntity<LoanResponseDto> update(@PathVariable Long id, @RequestBody LoanRequestDto entity) {
        logger.info("Updating loan with id: {}", id);
        LoanResponseDto response = loanService.update(id, entity);
        logger.info("Loan with id: {} updated successfully", id);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/getDeleteByIdLoan/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting loan with id: {}", id);
        ResponseEntity<String> response = super.delete(id);
        logger.info("Loan with id: {} deleted successfully", id);
        return response;
    }

    @GetMapping("/loanInformation")
    public ResponseEntity<List<LoanInformationResponseDto>> loanInformation() {
        List<LoanInformationResponseDto> loanResponseDtoList = loanService.getLoanInformation();
        return ResponseEntity.ok(loanResponseDtoList);
    }
}
