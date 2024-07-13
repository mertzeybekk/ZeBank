package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.InvoiceRequestDto;
import com.example.ZeBank.Dto.Response.InvoiceResponseDto;
import com.example.ZeBank.EntityLayer.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InvoiceService extends GenericService<Invoice, InvoiceRequestDto, InvoiceResponseDto> {
    InvoiceResponseDto updateInvoice(String invoiceNumber);

    Optional<List<InvoiceResponseDto>> findByInvoiceNumber(String invoiceNumber);
}
