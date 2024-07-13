package com.example.ZeBank.Controller;

import com.example.ZeBank.Dto.Request.AuthRequest;
import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Response.CustomerResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.Service.CustomerService;
import com.example.ZeBank.Service.GenericService;
import com.example.ZeBank.Service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/customers")
public class CustomerController extends GenericControllerImpl<Customer, CustomerRequestDto, CustomerResponseDto> {
    private CustomerService customerService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(GenericService<Customer, CustomerRequestDto, CustomerResponseDto> genericService, CustomerService customerService, JwtService jwtService, AuthenticationManager authenticationManager) {
        super(genericService);
        this.customerService = customerService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAll() {
        logger.info("Fetching all customers");
        List<CustomerResponseDto> customerResponseDto = customerService.findAll();
        return ResponseEntity.ok(customerResponseDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<CustomerResponseDto>> getById(@PathVariable Long id) {
        logger.info("Fetching customer by ID: {}", id);
        Optional<List<CustomerResponseDto>> customerResponseDto = customerService.findById(id);
        return ResponseEntity.ok(customerResponseDto.get());
    }

    @Override
    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CustomerRequestDto entity) {
        logger.info("Creating a new customer");
        CustomerResponseDto customerResponseDto = customerService.save(entity);
        return ResponseEntity.ok(customerResponseDto);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable Long id, @RequestBody CustomerRequestDto entity) {
        logger.info("Updating customer with ID: {}", id);
        CustomerResponseDto responseDto = customerService.update(id, entity);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting customer with ID: {}", id);
        customerService.delete(id);
        return ResponseEntity.ok("Delete");
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewUser(@RequestBody Customer userInfo) {
        logger.info("Adding new user");
        customerService.add(userInfo);
        return ResponseEntity.ok("User added successfully");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        String token ="";
        String fullToken="";
        logger.info("Authenticating user: {}", authRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            token= jwtService.generateToken(authRequest.getUsername());
            fullToken = jwtService.extractCustomClaim(token,"username");

            return ResponseEntity.ok(token + "," +fullToken);
        } else {
            logger.error("Authentication failed for user: {}", authRequest.getUsername());
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/creditScore/{id}")
    public ResponseEntity<CustomerResponseDto> creditScore(@PathVariable Long id) {
        CustomerResponseDto responseDto = customerService.creditScore(id);
        return ResponseEntity.ok(responseDto);
    }
}
