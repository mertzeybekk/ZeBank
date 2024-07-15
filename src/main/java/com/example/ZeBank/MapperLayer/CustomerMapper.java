package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Response.CustomerResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.CustomerType;
import com.example.ZeBank.Message.CustomerMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerMapper {
    private static PasswordEncoder passwordEncoder;

    public CustomerMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public static Customer mapToCustomer(CustomerRequestDto customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setPassword(customerRequest.getPassword());
        customer.setAddress(customerRequest.getAddress());
        customer.setContactInformation(customerRequest.getContactInformation());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setRoles(customerRequest.getRoles());
        return customer;
    }

    public static CustomerRequestDto mapToCustomerRequestDto(Customer entity) {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setId(entity.getId());
        customerRequestDto.setName(entity.getName());
        customerRequestDto.setAddress(entity.getAddress());
        customerRequestDto.setRoles(entity.getRoles());
        customerRequestDto.setPhoneNumber(entity.getPhoneNumber());
        customerRequestDto.setContactInformation(entity.getContactInformation());
        customerRequestDto.setDateOfBirth(entity.getDateOfBirth());
        return customerRequestDto;
    }

    public static CustomerResponseDto mapToCustomerResponseDto(Customer customer, CustomerType customerType) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setCreatedDate(new Date());
        customerResponseDto.setCustomerNumber(customer.getCustomerNumber());
        customerResponseDto.setInformationMessage(CustomerMessage.customerMessage(customer.getCustomerNumber(), customerType));
        return customerResponseDto;
    }

    public static Customer mapToSavedCustomer(Customer userInfo, PasswordEncoder passwordEncoder) {
        Customer customer = new Customer();
        customer.setName(userInfo.getName());
        customer.setAddress(userInfo.getAddress());
        customer.setContactInformation(userInfo.getContactInformation());
        customer.setDateOfBirth(userInfo.getDateOfBirth());
        customer.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        customer.setRoles(userInfo.getRoles());
        customer.setCreditScore(userInfo.getCreditScore());
        return customer;
    }

    public static CustomerResponseDto mapToCustomerResponse(Customer customer) {
        if (Objects.nonNull(customer)) {
            CustomerResponseDto customerResponseDto = new CustomerResponseDto();
            customerResponseDto.setCustomerNumber(customer.getCustomerNumber());
            customerResponseDto.setCreatedDate(new Date());
            customerResponseDto.setCreditScore(customer.getCreditScore());
            return customerResponseDto;
        }
        return null;

    }

    public static List<CustomerResponseDto> mapToCustomerResponseList(List<Customer> customerList) {
        return customerList.stream()
                .map(CustomerMapper::mapToCustomerResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
