package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.InvestmentRequestDto;
import com.example.ZeBank.Dto.Response.InvestmentResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.InvestmentType;
import com.example.ZeBank.EntityLayer.Investment;
import com.example.ZeBank.MapperLayer.InvestmentMapper;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import com.example.ZeBank.RepositoryLayer.InvestmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvestmentServiceImpl extends GenericServiceImpl<Investment, InvestmentRequestDto, InvestmentResponseDto> implements InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(InvestmentServiceImpl.class);

    public InvestmentServiceImpl(InvestmentRepository investmentRepository, CustomerRepository customerRepository) {
        super(investmentRepository);
        this.investmentRepository = investmentRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<InvestmentResponseDto> findAll() {
        logger.info("Finding all investments");
        List<Investment> investments = investmentRepository.findAll();
        logger.info("Found {} investments", investments.size());
        return InvestmentMapper.mapToInvestmentResponseDtoList(investments);
    }
    @Override
    public Optional<List<InvestmentResponseDto>> findById(Long id) {
        logger.info("Finding investment by id: {}", id);
        Optional<List<Investment>> investmentOptional = Optional.ofNullable(investmentRepository.findAllByCustomerId(id));

        if (investmentOptional.isPresent() && !investmentOptional.get().isEmpty()) {
            logger.info("Investment(s) found with id: {}", id);
            List<InvestmentResponseDto> investmentResponseDtos = investmentOptional.get().stream()
                    .map(InvestmentMapper::mapToInvestmentResponseDto)
                    .collect(Collectors.toList());
            return Optional.of(investmentResponseDtos);
        } else {
            logger.warn("Investment not found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public InvestmentResponseDto save(InvestmentRequestDto entity) {
        Optional<Customer> customerOptional = customerRepository.findById(Long.valueOf(entity.getCustomerId()));
        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with id: " + entity.getCustomerId());
        }

        Investment investment = new InvestmentMapper(customerRepository).dtoToEntity(entity);
        Investment savedInvestment = investmentRepository.save(investment);

        logger.info("Investment saved with id: {}", savedInvestment.getId());

        return InvestmentMapper.mapToInvestmentResponseDto(savedInvestment);
    }

    @Override
    public String delete(Long id) {
        Optional<Investment> investmentOptional = investmentRepository.findById(id);
        if (investmentOptional.isEmpty()) {
            throw new EntityNotFoundException("Investment not found with id: " + id);
        }
        Investment investment = investmentOptional.get();
        investmentRepository.delete(investment);
        logger.info("Investment deleted with id: {}", id);
        return "Investment deleted successfully";
    }

    @Override
    public InvestmentResponseDto update(Long id, InvestmentRequestDto entity) {
        logger.info("Updating investment with id: {}", id);
        Optional<Investment> investmentOptional = investmentRepository.findById(id);
        if (investmentOptional.isEmpty()) {
            throw new EntityNotFoundException("Investment not found with id: " + id);
        }

        Investment existingInvestment = investmentOptional.get();
        existingInvestment.setInvestmentType(InvestmentType.valueOf(entity.getInvestmentType()));
        existingInvestment.setCurrentMarketValue(entity.getCurrentMarketValue());
        existingInvestment.setInvestmentDate(entity.getInvestmentDate());
        existingInvestment.setInvestmentType(InvestmentType.valueOf(entity.getInvestmentType()));

        Investment updatedInvestment = investmentRepository.save(existingInvestment);
        logger.info("Investment updated with id: {}", updatedInvestment.getId());

        return InvestmentMapper.mapToInvestmentResponseDto(updatedInvestment);
    }
}
