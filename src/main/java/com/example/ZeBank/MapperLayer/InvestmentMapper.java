package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.CustomerRequestDto;
import com.example.ZeBank.Dto.Request.InvestmentRequestDto;
import com.example.ZeBank.Dto.Response.InvestmentResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.InvestmentType;
import com.example.ZeBank.EntityLayer.Investment;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvestmentMapper {
    private final CustomerRepository customerRepository;

    public InvestmentMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Investment dtoToEntity(InvestmentRequestDto investmentRequestDto) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findById(Long.valueOf(investmentRequestDto.getCustomerId()))
                .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı.")));
        if (Objects.nonNull(investmentRequestDto)) {
            Investment investment = new Investment();
            investment.setCurrentMarketValue(investmentRequestDto.getCurrentMarketValue());
            investment.setInvestmentDate(investmentRequestDto.getInvestmentDate());
            investment.setInvestmentType(InvestmentType.valueOf(investmentRequestDto.getInvestmentType()));
            investment.setPurchasePrice(investmentRequestDto.getPurchasePrice());
            investment.setCustomer(customerOptional.get());
            return investment;
        }
        return null;
    }

    public static InvestmentResponseDto mapToInvestmentResponseDto(Investment investment) {
        if (Objects.nonNull(investment)) {
            InvestmentResponseDto investmentResponseDto = new InvestmentResponseDto();
            investmentResponseDto.setInvestmentDate(investment.getInvestmentDate());
            investmentResponseDto.setInformationMessage(difference(investment.getCurrentMarketValue(),investment.getPurchasePrice()));
            investmentResponseDto.setPurchasePrice(investment.getPurchasePrice());
            investmentResponseDto.setCurrentMarketValue(investment.getCurrentMarketValue());
            investmentResponseDto.setId(Math.toIntExact(investment.getId()));
            investmentResponseDto.setInvestmentType(String.valueOf(investment.getInvestmentType().getDisplayName()));
            return investmentResponseDto;
        }
        return null;
    }

    private static String difference(double currentMarketValue, double purchasePrice) {
        String informationMessage = "";
        double differenceValue = currentMarketValue - purchasePrice;

        if (differenceValue >= 0) {
            informationMessage = String.format("%.2f TL Karınızda", differenceValue);
        } else {
            informationMessage = String.format("%.2f TL Zarardasınız", Math.abs(differenceValue));
        }

        return informationMessage;
    }


    public static List<InvestmentResponseDto> mapToInvestmentResponseDtoList(List<Investment> investmentList) {
        return investmentList.stream()
                .map(InvestmentMapper::mapToInvestmentResponseDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
