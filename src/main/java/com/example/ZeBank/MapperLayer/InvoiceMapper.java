package com.example.ZeBank.MapperLayer;

import com.example.ZeBank.Dto.Request.InvoiceRequestDto;
import com.example.ZeBank.Dto.Response.InvestmentResponseDto;
import com.example.ZeBank.Dto.Response.InvoiceResponseDto;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Investment;
import com.example.ZeBank.EntityLayer.Invoice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InvoiceMapper {
    public static InvoiceResponseDto mapToInvoiceResponseDto(Invoice invoice) {
        if (Objects.nonNull(invoice)) {
            InvoiceResponseDto responseDto = new InvoiceResponseDto();
            responseDto.setInvoiceAmount(invoice.getTotalAmount());
            responseDto.setInvoicetDate(invoice.getInvoiceDate());
            responseDto.setInformationMessage(" Faturanız başarıyla Oluşturuldu. " + " Fatura Numaranız : " + invoice.getInvoiceNumber());
            responseDto.setInvoiceType(invoice.getInvoiceType().getDisplayName());
            responseDto.setCompany(invoice.getCompany());
            responseDto.setInvoiceNumber(invoice.getInvoiceNumber());
            return responseDto;
        }
        return null;
    }


    public static List<InvoiceResponseDto> mapToInvoiceList(List<Invoice> invoiceList) {
        return invoiceList.stream()
                .map(InvoiceMapper::mapToInvoiceResponseDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static Invoice mapToInvoice(InvoiceRequestDto invoiceRequestDto, Customer customer) {
        if (Objects.nonNull(invoiceRequestDto)) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceDate(invoiceRequestDto.getInvoiceDate());
            invoice.setInvoiceNumber(invoiceRequestDto.getInvoiceNumber());
            invoice.setInvoiceType(invoiceRequestDto.getInvoiceType());
            invoice.setCompany(invoiceRequestDto.getCompany());
            invoice.setTotalAmount(invoiceRequestDto.getTotalAmount());
            invoice.setPaymentDate(invoiceRequestDto.getInvoiceDate());
            invoice.setCustomer(customer);
            invoice.setPaid(Boolean.TRUE);
            return invoice;

        }
        return null;
    }

}
