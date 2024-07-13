package com.example.ZeBank.MapperLayer;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.Dto.Request.InsuranceRequestDto;
import com.example.ZeBank.Dto.Response.InsuranceResponseDto;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Enum.InsuranceType;
import com.example.ZeBank.EntityLayer.Insurance;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import com.example.ZeBank.RepositoryLayer.AccountRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class InsuranceMapper {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public InsuranceMapper(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public Insurance dtoToEntity(InsuranceRequestDto dto) {
        Insurance insurance = new Insurance();
        insurance.setType(InsuranceType.valueOf(String.valueOf(dto.getType())));

        Customer customer = customerRepository.findById(Long.valueOf(dto.getCustomerId()))
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + dto.getCustomerId()));
        insurance.setCustomer(customer);

        Account account = accountRepository.findById(Long.valueOf(dto.getAccountId()))
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + dto.getAccountId()));
        insurance.setAccount(account);

        return insurance;
    }

    public InsuranceResponseDto entityToDto(Insurance entity) {
        InsuranceResponseDto dto = new InsuranceResponseDto();
        dto.setCustomerId(entity.getCustomer().getId());
        dto.setAccountId(entity.getAccount().getId());
        dto.setType(entity.getType().name());
        return dto;
    }

    public List<InsuranceResponseDto> mapToInsuranceResponseDtoList(List<Insurance> insurances) {
        return insurances.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public static InsuranceResponseDto mapToInsuranceResponseDto(Insurance insurance) {
        InsuranceResponseDto dto = new InsuranceResponseDto();
        dto.setId(insurance.getId());
        dto.setCustomerId(insurance.getCustomer().getId());
        dto.setAccountId(insurance.getAccount().getId());
        dto.setType(insurance.getType().name());
        return dto;
    }
}
