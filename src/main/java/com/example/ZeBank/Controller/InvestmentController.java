package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.InvestmentRequestDto;
import com.example.ZeBank.Dto.Response.InvestmentResponseDto;
import com.example.ZeBank.EntityLayer.Investment;
import com.example.ZeBank.Service.InvestmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/investments")
public class InvestmentController extends GenericControllerImpl<Investment, InvestmentRequestDto, InvestmentResponseDto> {
    private static final Logger logger = LoggerFactory.getLogger(InvestmentController.class);
    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        super(investmentService);
        this.investmentService = investmentService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<InvestmentResponseDto>> getAll() {
        logger.info("Fetching all investments");
        List<InvestmentResponseDto> investmentResponseDto = investmentService.findAll();
        logger.info("Fetched {} investments", investmentResponseDto.size());
        return ResponseEntity.ok(investmentResponseDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<InvestmentResponseDto>> getById(@PathVariable Long id) {
        logger.info("Fetching investment with ID: {}", id);
        Optional<List<InvestmentResponseDto>> investmentResponseDto = investmentService.findById(id);
        if (investmentResponseDto.isPresent()) {
            logger.info("Fetched investment with ID: {}", id);
            return ResponseEntity.ok(investmentResponseDto.get());
        } else {
            logger.warn("Investment with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<InvestmentResponseDto> create(@RequestBody InvestmentRequestDto entity) {
        logger.info("Creating a new investment");
        InvestmentResponseDto investmentResponseDto = investmentService.save(entity);
        logger.info("Created a new investment with ID: {}", investmentResponseDto.getId());
        return ResponseEntity.ok(investmentResponseDto);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<InvestmentResponseDto> update(@PathVariable Long id, @RequestBody InvestmentRequestDto entity) {
        logger.info("Updating investment with ID: {}", id);
        InvestmentResponseDto investmentResponseDto = investmentService.update(id, entity);
        logger.info("Updated investment with ID: {}", id);
        return ResponseEntity.ok(investmentResponseDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting investment with ID: {}", id);
        investmentService.delete(id);
        logger.info("Deleted investment with ID: {}", id);
        return ResponseEntity.ok("Investment deleted successfully");
    }
}
