package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.AccountRequestDto;
import com.example.ZeBank.Dto.Request.CreditCardPayingBillsRequest;
import com.example.ZeBank.Dto.Request.CreditCardRequestDto;
import com.example.ZeBank.Dto.Response.AccountResponseDto;
import com.example.ZeBank.Dto.Response.CreditCardResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.CreditCard;
import com.example.ZeBank.Service.AccountService;
import com.example.ZeBank.Service.CreditCardService;
import com.example.ZeBank.Service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController extends GenericControllerImpl<CreditCard, CreditCardRequestDto, CreditCardResponseDto> {
    private CreditCardService creditCardService;
    private static final Logger logger = LoggerFactory.getLogger(CreditCardController.class);

    public CreditCardController(GenericService<CreditCard, CreditCardRequestDto, CreditCardResponseDto> genericService, CreditCardService creditCardService) {
        super(genericService);
        this.creditCardService = creditCardService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CreditCardResponseDto>> getAll() {
        logger.info("Fetching all credit cards");
        List<CreditCardResponseDto> response = creditCardService.findAll();
        logger.info("Successfully fetched all credit cards");
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{creditCardId}")
    public ResponseEntity<List<CreditCardResponseDto>> getById(@PathVariable Long creditCardId) {
        logger.info("Fetching credit card by ID: {}", creditCardId);
        Optional<List<CreditCardResponseDto>> response = creditCardService.findById(creditCardId);
        if (response.isPresent()) {
            logger.info("Successfully fetched credit card by ID: {}", creditCardId);
            return ResponseEntity.ok(response.get());
        } else {
            logger.warn("Credit card not found with ID: {}", creditCardId);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping("/applyCreditCard")
    public ResponseEntity<CreditCardResponseDto> create(@RequestBody CreditCardRequestDto entity) {
        logger.info("Creating new credit card with request: {}", entity);
        CreditCardResponseDto response = creditCardService.save(entity);
        logger.info("Successfully created credit card: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{creditCardId}")
    public ResponseEntity<CreditCardResponseDto> update(@PathVariable Long creditCardId, @RequestBody CreditCardRequestDto entity) {
        logger.info("Updating credit card with ID: {} and request: {}", creditCardId, entity);
        CreditCardResponseDto response = creditCardService.update(creditCardId, entity);
        logger.info("Successfully updated credit card with ID: {}", creditCardId);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{creditCardId}")
    public ResponseEntity<String> delete(@PathVariable Long creditCardId) {
        logger.info("Deleting credit card with ID: {}", creditCardId);
        String response = creditCardService.delete(creditCardId);
        logger.info("Successfully deleted credit card with ID: {}", creditCardId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{invoiceNumber}")
    public ResponseEntity<CreditCardResponseDto> transactionCreditCard(@RequestBody CreditCardPayingBillsRequest creditCardPayingBillsRequest, @PathVariable String invoiceNumber) {
        logger.info("Updating credit card Amount with Amount: {}", creditCardPayingBillsRequest.getInvoiceAmount());
        CreditCardResponseDto response = creditCardService.creditCardTransaction(creditCardPayingBillsRequest,invoiceNumber);
        logger.info("Successfully Updated credit card with Amount: {}", response.getBalance());
        return ResponseEntity.ok(response);
    }
}
