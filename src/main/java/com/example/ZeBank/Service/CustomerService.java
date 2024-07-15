package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Response.CustomerResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService extends GenericService<Customer, CustomerRequestDto, CustomerResponseDto> {
    public String add(Customer userInfo);

    @Override
    List<CustomerResponseDto> findAll();

    @Override
    Optional<List<CustomerResponseDto>> findById(Long id);

    @Override
    CustomerResponseDto save(CustomerRequestDto entity);

    @Override
    String delete(Long id);

    CustomerResponseDto creditScore(Long id);
    Integer customerUsernameGetId(String username);
}
