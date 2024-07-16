package com.example.ZeBank.Service;

import com.example.ZeBank.CustomException.ResourceNotFoundException;
import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Response.CustomerResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.CustomerType;
import com.example.ZeBank.MapperLayer.CustomerMapper;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import com.example.ZeBank.Util.PasswordUtil;
import com.example.ZeBank.Util.SmsSend;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Primary
public class CustomerServiceImpl extends GenericServiceImpl<Customer, CustomerRequestDto, CustomerResponseDto> implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        super(customerRepository);
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String add(Customer userInfo) {
        log.info("Adding new customer: {}", userInfo);
        Customer customer = CustomerMapper.mapToSavedCustomer(userInfo, passwordEncoder);
        customerRepository.save(customer);
        log.info("Customer successfully created: {}", customer);
        return "User successfully created";
    }

    @Override
    public List<CustomerResponseDto> findAll() {
        log.info("Fetching all customers");
        List<Customer> customerList = customerRepository.findAll();
        log.info("Found {} customers", customerList.size());
        return CustomerMapper.mapToCustomerResponseList(customerList);
    }

    @Override
    public Optional<List<CustomerResponseDto>> findById(Long id) {
        log.info("Fetching customer by id: {}", id);
        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.ifPresent(customer ->
                log.info("Customer found. Customer ID: {}, Name: {}", customer.getId(), customer.getName()));
        return Optional.ofNullable(Collections.singletonList(CustomerMapper.mapToCustomerResponse(customerOptional.get())));
    }

    @Override
    public CustomerResponseDto save(CustomerRequestDto entity) {
        log.info("Creating new customer: {}", entity);
        Customer customer = CustomerMapper.mapToCustomer(entity);
        customer.setPassword(passwordEncoder.encode(entity.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);
        String message = String.format("""
                        Müşteri Numarası: %s 'lı hesabınız oluşturuldu.,
                        İşlem  Tarihi %s,             
                        """,
                customer.getCustomerNumber(),
                new Date().toString()
        );
        SmsSend.sendTokenSms(message);
        log.info("Customer successfully created: {}", savedCustomer);
        return CustomerMapper.mapToCustomerResponseDto(savedCustomer, CustomerType.CREATED);
    }

    @Override
    public String delete(Long id) {
        log.info("Deleting customer by id: {}", id);
        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.ifPresentOrElse(customer -> {
            customerRepository.deleteById(id);
            log.info("Customer deleted. Customer ID: {}, Name: {}", customer.getId(), customer.getName());
        }, () -> {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        });
        String message = String.format("""
                        Müşteri Numarası: %s 'lı hesabınız silindi.,
                        İşlem Tarihi %s,             
                        """,
                customerOptional.get().getCustomerNumber(),
                new Date().toString()
        );
        SmsSend.sendTokenSms(message);
        return "Customer Deleted";
    }

    @Override
    public CustomerResponseDto update(Long id, CustomerRequestDto customerDto) {
        log.info("Updating customer with id: {}", id);
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        existingCustomer.setName(customerDto.getName());
        existingCustomer.setAddress(customerDto.getAddress());
        existingCustomer.setContactInformation(customerDto.getContactInformation());
        existingCustomer.setDateOfBirth(customerDto.getDateOfBirth());
        existingCustomer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Customer successfully updated: {}", updatedCustomer);
        return CustomerMapper.mapToCustomerResponseDto(updatedCustomer, CustomerType.UPDATING);
    }

    @Override
    public CustomerResponseDto creditScore(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        String message = String.format("""
                        Kredi Skorunuz: %s,           
                        """,
                customer.get().getCreditScore()
                );
        SmsSend.sendTokenSms(message);
        return CustomerMapper.mapToCustomerResponse(customer.get());
    }

    @Override
    public Integer customerUsernameGetId(String username) {
        Optional<Customer> userInfo = customerRepository.findByName(username);
        return Math.toIntExact(userInfo.get().getId());
    }
}
