package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.TransactionRequestDto;
import com.example.ZeBank.Dto.Response.TransactionResponseDto;
import com.example.ZeBank.EntityLayer.Transaction;
import com.example.ZeBank.MapperLayer.TransactionMapper;
import com.example.ZeBank.Service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController extends GenericControllerImpl<Transaction, TransactionRequestDto, TransactionResponseDto> {
    private final TransactionService transactionService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionService transactionService) {
        super(transactionService);
        this.transactionService = transactionService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAll() {
        logger.info("Fetching all transactions");
        List<TransactionResponseDto> transactions = transactionService.findAll();
        logger.info("Found {} transactions", transactions.size());
        return ResponseEntity.ok(transactions);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionResponseDto>> getById(@PathVariable Long id) {
        logger.info("Fetching transaction by id: {}", id);
        Optional<List<TransactionResponseDto>> response = transactionService.findById(id);
        logger.info("Transaction fetched by id: {}", id);
        return ResponseEntity.ok(response.get());
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<TransactionResponseDto> create(@RequestBody TransactionRequestDto entity) {
        logger.info("Creating a new transaction");
        TransactionResponseDto response = transactionService.save(entity);
        logger.info("New transaction created successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransactionResponseDto> update(Long id, TransactionRequestDto entity) {
        logger.info("Updating transaction with id: {}", id);
        ResponseEntity<TransactionResponseDto> response = super.update(id, entity);
        logger.info("Transaction with id: {} updated successfully", id);
        return response;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting transaction with id: {}", id);
        ResponseEntity<String> response = super.delete(id);
        logger.info("Transaction with id: {} deleted successfully", id);
        return response;
    }
}
