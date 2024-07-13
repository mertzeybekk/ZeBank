package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.LoanRequestDto;
import com.example.ZeBank.Dto.Response.LoanInformationResponseDto;
import com.example.ZeBank.Dto.Response.LoanResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.LoanStatus;
import com.example.ZeBank.EntityLayer.Enum.LoanType;
import com.example.ZeBank.EntityLayer.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoanMapper {

    private static final Logger log = LoggerFactory.getLogger(LoanMapper.class);

    public static Loan dtoToEntity(LoanRequestDto loanRequestDto, Customer customer) {
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanType(LoanType.valueOf(loanRequestDto.getLoanType()));
        loan.setLoanAmount(loanRequestDto.getLoanAmount());
        loan.setInterestRate(loanRequestDto.getInterestRate());
        loan.setPaymentStart(loanRequestDto.getPaymentStart());
        loan.setPaymentEnd(loanRequestDto.getPaymentEnd());
        loan.setLoanDurationMonths(loanRequestDto.getLoanDurationMonths());
        loan.setLoanStatus(LoanStatus.mapToLoanStatusRequest(loanRequestDto.getLoanStatus()));
        return loan;
    }

    public static LoanResponseDto entityToDto(Loan loan) {
        LoanResponseDto loanResponseDto = new LoanResponseDto();
        loanResponseDto.setInformationMessage("Loan successfully processed for customer ID: " + loan.getCustomer().getId());
        loanResponseDto.setLoanPrice(loan.getLoanAmount());
        loanResponseDto.setPaymentStart(loan.getPaymentStart());
        loanResponseDto.setPaymentEnd(loan.getPaymentEnd());
        loanResponseDto.setLoanDurationMonths(loan.getLoanDurationMonths());
        loanResponseDto.setLoanStatus(LoanStatus.mapToLoanStatus(loan.getLoanStatus()));
        loanResponseDto.setLoanType(LoanType.mapToLoanType(loan.getLoanType()));
        loanResponseDto.setRemainingAmount(loan.getRemainingAmount());

        double monthlyAmount = calculateMonthlyAmount(loan.getLoanAmount(), loan.getInterestRate(), loan.getLoanDurationMonths());
        loanResponseDto.setMonthlyAmount(monthlyAmount);

        return loanResponseDto;
    }

    private static double calculateMonthlyAmount(double loanAmount, double interestRate, int months) {
        // Placeholder for actual calculation logic. Here we assume a simple interest calculation.
        double totalInterest = loanAmount * (interestRate / 100) * (months / 12.0);
        double totalPayment = loanAmount + totalInterest;
        return totalPayment / months;
    }

    public static List<LoanResponseDto> mapToLoanResponseDtoList(List<Loan> loan) {
        return loan.stream()
                .map(LoanMapper::mapToLoanResponseDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static LoanResponseDto mapToLoanResponseDto(Loan loan) {
        if (Objects.nonNull(loan)) {
            LoanResponseDto responseDto = new LoanResponseDto();
            responseDto.setLoanPrice(loan.getLoanAmount());
            responseDto.setPaymentEnd(loan.getPaymentEnd());
            responseDto.setPaymentStart(loan.getPaymentStart());
            responseDto.setLoanDurationMonths(loan.getLoanDurationMonths());
            double monthlyAmount = calculateMonthlyAmount(loan.getLoanAmount(), loan.getInterestRate(), loan.getLoanDurationMonths());
            responseDto.setMonthlyAmount(monthlyAmount);
            responseDto.setInformationMessage("Kredi Başlangıç  Tarih : " + loan.getPaymentStart());
            responseDto.setLoanType(LoanType.mapToLoanType((loan.getLoanType())));
            responseDto.setLoanStatus(LoanStatus.mapToLoanStatus(loan.getLoanStatus()));
            responseDto.setRemainingAmount(loan.getRemainingAmount());
            responseDto.setLoanId(loan.getId());
            return responseDto;
        }
        return null;
    }

    public static List<LoanInformationResponseDto> mapToLoanInformation() {
        List<LoanInformationResponseDto> responseDtoList = new ArrayList<>();
     log.info(String.valueOf(LoanType.map()));
   /*     List<String> loanTypeList = Stream.of(LoanType.values())
                .map(LoanType::getLoanTypeName)
                .collect(Collectors.toList());

        List<String> loanStatusList = Stream.of(LoanStatus.values())
                .map(LoanStatus::getLoanStatus)
                .collect(Collectors.toList());*/

        LoanInformationResponseDto responseDto = new LoanInformationResponseDto();
      //  responseDto.setLoanType(loanTypeList);
        responseDto.setLoanTypeMap(LoanType.map());
        responseDtoList.add(responseDto);

        return responseDtoList;
    }
}
