package com.example.ZeBank.RepositoryLayer;

import com.example.ZeBank.EntityLayer.Invoice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends BaseRepository<Invoice> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    List<Invoice> findByPaidTrue();
}
