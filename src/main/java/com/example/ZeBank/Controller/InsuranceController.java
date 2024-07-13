package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.InsuranceRequestDto;
import com.example.ZeBank.Dto.Response.InsuranceResponseDto;
import com.example.ZeBank.EntityLayer.Insurance;
import com.example.ZeBank.Service.InsuranceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insurance")
public class InsuranceController extends GenericControllerImpl<Insurance, InsuranceRequestDto, InsuranceResponseDto> {
    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);
    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        super(insuranceService);
        this.insuranceService = insuranceService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<InsuranceResponseDto>> getAll() {
        logger.info("Fetching all insurances");
        List<InsuranceResponseDto> insuranceResponseDto = insuranceService.findAll();
        logger.info("Fetched {} insurances", insuranceResponseDto.size());
        return ResponseEntity.ok(insuranceResponseDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<InsuranceResponseDto>> getById(@PathVariable Long id) {
        logger.info("Fetching insurance with ID: {}", id);
        Optional<List<InsuranceResponseDto>> responseDto = insuranceService.findById(id);
        if (responseDto.isPresent()) {
            logger.info("Fetched insurance with ID: {}", id);
            return ResponseEntity.of(Optional.of(responseDto.get()));
        } else {
            logger.warn("Insurance with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<InsuranceResponseDto> create(@RequestBody InsuranceRequestDto entity) {
        logger.info("Creating a new insurance");
        InsuranceResponseDto insuranceResponseDto = insuranceService.save(entity);
        logger.info("Created a new insurance with ID: {}", insuranceResponseDto.getId());
        return ResponseEntity.ok(insuranceResponseDto);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<InsuranceResponseDto> update(@PathVariable Long id, @RequestBody InsuranceRequestDto entity) {
        logger.info("Updating insurance with ID: {}", id);
        InsuranceResponseDto insuranceResponseDto = insuranceService.update(id, entity);
        logger.info("Updated insurance with ID: {}", id);
        return ResponseEntity.ok(insuranceResponseDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting insurance with ID: {}", id);
        insuranceService.delete(id);
        logger.info("Deleted insurance with ID: {}", id);
        return ResponseEntity.ok("Insurance deleted successfully");
    }
}
