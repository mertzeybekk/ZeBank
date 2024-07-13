package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.InvestmentRequestDto;
import com.example.ZeBank.Dto.Request.InvoiceRequestDto;
import com.example.ZeBank.Dto.Response.InvestmentResponseDto;
import com.example.ZeBank.Dto.Response.InvoiceResponseDto;
import com.example.ZeBank.EntityLayer.Investment;
import com.example.ZeBank.EntityLayer.Invoice;
import com.example.ZeBank.Service.GenericService;
import com.example.ZeBank.Service.InvestmentService;
import com.example.ZeBank.Service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceController extends GenericControllerImpl<Invoice, InvoiceRequestDto, InvoiceResponseDto> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoiceService invoiceService;

    public InvoiceController(GenericService<Invoice, InvoiceRequestDto, InvoiceResponseDto> genericService, InvoiceService invoiceService) {
        super(genericService);
        this.invoiceService = invoiceService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<InvoiceResponseDto>> getAll() {
        logger.info("Request to get all invoices received");
        List<InvoiceResponseDto> invoiceResponseDtos = invoiceService.findAll();
        logger.info("Successfully retrieved all invoices, count: {}", invoiceResponseDtos.size());
        return ResponseEntity.ok(invoiceResponseDtos);
    }

    @Override
    public ResponseEntity<List<InvoiceResponseDto>> getById(@PathVariable Long id) {
        logger.info("Request to get invoices by ID: {}", id);
        Optional<List<InvoiceResponseDto>> invoices = invoiceService.findById(id);
        if (invoices.isPresent()) {
            logger.info("Successfully retrieved invoices for ID: {}", id);
            return ResponseEntity.ok(invoices.get());
        } else {
            logger.warn("No invoices found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping("/save")
    public ResponseEntity<InvoiceResponseDto> create(@RequestBody InvoiceRequestDto entity) {
        logger.info("Request to create a new invoice received, Customer ID: {}", entity.getCustomerId());
        InvoiceResponseDto invoice = invoiceService.save(entity);
        logger.info("Successfully created invoice with ID: {}", invoice.getInvoiceAmount());
        return ResponseEntity.ok(invoice);
    }

    @Override
    public ResponseEntity<InvoiceResponseDto> update(@PathVariable Long id, @RequestBody InvoiceRequestDto entity) {
        logger.info("Request to update invoice with ID: {} received", id);
        InvoiceResponseDto updatedInvoice = invoiceService.update(id, entity);
        if (updatedInvoice != null) {
            logger.info("Successfully updated invoice with ID: {}", id);
            return ResponseEntity.ok(updatedInvoice);
        } else {
            logger.warn("No invoice found to update with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Request to delete invoice with ID: {} received", id);
        String result = invoiceService.delete(id);
        logger.info("Successfully deleted invoice with ID: {}", id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{invoiceNumber}")
    public ResponseEntity<InvoiceResponseDto> updateInvoice(@PathVariable String invoiceNumber) {
        logger.info("Request to update invoice with invoice number: {} received", invoiceNumber);
        InvoiceResponseDto responseDto = invoiceService.updateInvoice(invoiceNumber);
        logger.info("Successfully updated invoice with invoice number: {}", invoiceNumber);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<Optional<List<InvoiceResponseDto>>> getInvoiceNumber(@PathVariable String invoiceNumber) {
        logger.info("Request to retrieve invoice with invoice number: {} received", invoiceNumber);
        Optional<List<InvoiceResponseDto>> responseDto = invoiceService.findByInvoiceNumber(invoiceNumber);

        if (responseDto.isPresent()) {
            logger.info("Successfully retrieved invoice with invoice number: {}", invoiceNumber);
            return ResponseEntity.ok(responseDto);
        } else {
            logger.warn("Invoice with invoice number: {} not found", invoiceNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

