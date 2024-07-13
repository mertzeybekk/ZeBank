package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.InsuranceRequestDto;
import com.example.ZeBank.Dto.Response.InsuranceResponseDto;
import com.example.ZeBank.EntityLayer.Enum.InsuranceType;
import com.example.ZeBank.EntityLayer.Insurance;
import com.example.ZeBank.MapperLayer.InsuranceMapper;
import com.example.ZeBank.RepositoryLayer.AccountRepository;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import com.example.ZeBank.RepositoryLayer.InsuranceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsuranceServiceImpl extends GenericServiceImpl<Insurance, InsuranceRequestDto, InsuranceResponseDto> implements InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private static final Logger logger = LoggerFactory.getLogger(InsuranceServiceImpl.class);

    public InsuranceServiceImpl(InsuranceRepository insuranceRepository, CustomerRepository customerRepository, AccountRepository accountRepository) {
        super(insuranceRepository);
        this.insuranceRepository = insuranceRepository;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<InsuranceResponseDto> findAll() {
        logger.info("Finding all insurances");
        List<Insurance> insurances = insuranceRepository.findAll();
        logger.info("Found {} insurances", insurances.size());

        return new InsuranceMapper(customerRepository, accountRepository).mapToInsuranceResponseDtoList(insurances);
    }

    @Override
    public Optional<List<InsuranceResponseDto>> findById(Long id) {
        logger.info("Finding insurance by id: {}", id);
        Optional<List<Insurance>> insuranceOptional = insuranceRepository.findAllById(id);

        if (insuranceOptional.isPresent() && !insuranceOptional.get().isEmpty()) {
            logger.info("Insurance(s) found with id: {}", id);
            List<InsuranceResponseDto> insuranceResponseDtos = insuranceOptional.get().stream()
                    .map(InsuranceMapper::mapToInsuranceResponseDto)
                    .collect(Collectors.toList());
            return Optional.of(insuranceResponseDtos);
        } else {
            logger.warn("Insurance not found with id: {}", id);
            return Optional.empty();
        }
    }


    @Override
    public InsuranceResponseDto save(InsuranceRequestDto entity) {
        logger.info("Saving insurance for customer id: {}", entity.getCustomerId());
        Insurance insurance = new InsuranceMapper(customerRepository, accountRepository).dtoToEntity(entity);
        Insurance savedInsurance = insuranceRepository.save(insurance);
        logger.info("Insurance saved with id: {}", savedInsurance.getId());
        return InsuranceMapper.mapToInsuranceResponseDto(savedInsurance);
    }

    @Override
    public String delete(Long id) {
        logger.info("Deleting insurance with id: {}", id);
        Optional<Insurance> insuranceOptional = insuranceRepository.findById(id);
        if (insuranceOptional.isEmpty()) {
            throw new EntityNotFoundException("Insurance not found with id: " + id);
        }
        insuranceRepository.deleteById(id);
        logger.info("Insurance deleted with id: {}", id);
        return "Insurance deleted successfully";
    }

    @Override
    public InsuranceResponseDto update(Long id, InsuranceRequestDto entity) {
        logger.info("Updating insurance with id: {}", id);
        Optional<Insurance> insuranceOptional = insuranceRepository.findById(id);
        if (insuranceOptional.isEmpty()) {
            throw new EntityNotFoundException("Insurance not found with id: " + id);
        }
        Insurance existingInsurance = insuranceOptional.get();
        existingInsurance.setType(InsuranceType.valueOf(entity.getType()));
        existingInsurance.setCustomer(customerRepository.findById(Long.valueOf(entity.getCustomerId())).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + entity.getCustomerId())));
        existingInsurance.setAccount(accountRepository.findById(Long.valueOf(entity.getAccountId())).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + entity.getAccountId())));

        Insurance updatedInsurance = insuranceRepository.save(existingInsurance);
        logger.info("Insurance updated with id: {}", updatedInsurance.getId());
        return InsuranceMapper.mapToInsuranceResponseDto(updatedInsurance);
    }
}
