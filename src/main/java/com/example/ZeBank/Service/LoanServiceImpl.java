package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.LoanRequestDto;
import com.example.ZeBank.Dto.Request.SmsTokenRequest;
import com.example.ZeBank.Dto.Response.LoanInformationResponseDto;
import com.example.ZeBank.Dto.Response.LoanResponseDto;
import com.example.ZeBank.Email.EmailService;
import com.example.ZeBank.EntityLayer.Account;
import com.example.ZeBank.EntityLayer.Customer;
import com.example.ZeBank.EntityLayer.Enum.LoanStatus;
import com.example.ZeBank.EntityLayer.Loan;
import com.example.ZeBank.MapperLayer.LoanMapper;
import com.example.ZeBank.RepositoryLayer.CustomerRepository;
import com.example.ZeBank.RepositoryLayer.LoanRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class LoanServiceImpl extends GenericServiceImpl<Loan, LoanRequestDto, LoanResponseDto> implements LoanService {
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private SmsTokenService smsTokenService;
    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private String TWILIO_PHONE_NUMBER;

    public LoanServiceImpl(LoanRepository loanRepository, CustomerRepository customerRepository, EmailService emailService
            , SmsTokenService smsTokenService) {
        super(loanRepository);
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.emailService = emailService;
        this.smsTokenService = smsTokenService;
    }

    @Override
    public List<LoanResponseDto> findAll() {
        logger.info("Finding all loans");
        List<Loan> loans = loanRepository.findAll();
        logger.info("Found {} loans", loans.size());
        return LoanMapper.mapToLoanResponseDtoList(loans);
    }

    @Override
    public Optional<List<LoanResponseDto>> findById(Long id) {
        logger.info("Finding loans by id: {}", id);
        Optional<List<Loan>> loanOptional = loanRepository.findAllByCustomerId(id);

        if (loanOptional.isPresent() && !loanOptional.get().isEmpty()) {
            logger.info("Loans found with id: {}", id);
            List<LoanResponseDto> loanResponseDtos = loanOptional.get().stream()
                    .map(LoanMapper::mapToLoanResponseDto)
                    .collect(Collectors.toList());
            return Optional.of(loanResponseDtos);
        } else {
            logger.warn("Loan not found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public LoanResponseDto save(LoanRequestDto entity) {
        Customer customer = customerRepository.findById(Long.valueOf(entity.getCustomerId()))
                .orElseThrow(() -> {
                    logger.error("Customer not found with id: {}", entity.getCustomerId());
                    return new EntityNotFoundException("Customer not found with id: " + entity.getCustomerId());
                });

        List<Account> accountList = customer.getAccounts();
        Optional<List<Loan>> loanList = loanRepository.findAllByCustomerId(entity.getCustomerId());

        boolean hasActiveLoan = loanList.isPresent() && loanList.get().stream()
                .anyMatch(loan -> loan.getLoanStatus() == LoanStatus.ACTIVE);

        boolean hasPendingLoan = loanList.isPresent() && loanList.get().stream()
                .anyMatch(loan -> loan.getLoanStatus() == LoanStatus.PENDING);

        boolean hasRejectedLoan = loanList.isPresent() && loanList.get().stream()
                .anyMatch(loan -> loan.getLoanStatus() == LoanStatus.REJECTED);

        boolean hasSufficientBalance = accountList.stream()
                .anyMatch(account -> account.getBalance() >= entity.getLoanAmount());

        if (hasActiveLoan) {
            sendTokenSms("Aktif bir krediniz var. Lütfen yeni bir krediye başvurmadan önce mevcut kredinizi ödeyin.");
            return new LoanResponseDto("Aktif bir krediniz var. Lütfen yeni bir krediye başvurmadan önce mevcut kredinizi ödeyin.");
        } else if (hasPendingLoan) {
            sendTokenSms("Bekleyen bir kredi başvurunuz var. Lütfen mevcut kredi başvurunuzun onaylanmasını veya reddedilmesini bekleyin.");
            return new LoanResponseDto("Bekleyen bir kredi başvurunuz var. Lütfen mevcut kredi başvurunuzun onaylanmasını veya reddedilmesini bekleyin.");
        } else if (hasRejectedLoan) {
            sendTokenSms("Önceki kredi başvurunuz reddedildi. Lütfen uygunluk kriterlerinizi kontrol edin ve daha sonra tekrar deneyin..");
            return new LoanResponseDto("Önceki kredi başvurunuz reddedildi. Lütfen uygunluk kriterlerinizi kontrol edin ve daha sonra tekrar deneyin.");
        } else if (!hasSufficientBalance) {
            sendTokenSms("Hesaplarınızda yetersiz bakiye. Krediye başvurmadan önce lütfen hesaplarınıza para yatırın..");
            return new LoanResponseDto("Hesaplarınızda yetersiz bakiye. Krediye başvurmadan önce lütfen hesaplarınıza para yatırın.");
        } else {
            entity.setLoanStatus(String.valueOf(LoanStatus.APPROVED));
        }

        Loan loan = LoanMapper.mapToLoan(entity, customer);
        Loan savedLoan = loanRepository.save(loan);
        String message = String.format("""
                        Kredi Miktarı: %.2f TL,
                        Vade (Ay): %d,
                        Durum: %s,
                        Ödeme Başlangıç Tarihi: %s,
                        Ödeme Bitiş Tarihi: %s,
                        Faiz Oranı: %.2f%%
                        """,
                savedLoan.getLoanAmount(),
                savedLoan.getLoanDurationMonths(),
                LoanStatus.APPROVED,
                savedLoan.getPaymentStart(),
                savedLoan.getPaymentEnd(),
                savedLoan.getInterestRate()
        );
        emailService.sendSimpleEmail("zeybekmertanil@gmail.com", "ZeBank Kredi Başvuru Sonucu", message);
        sendTokenSms(message);
        logger.info("Loan saved: {}", savedLoan);
        return LoanMapper.mapToLoanResponse(savedLoan);
    }

    public void sendTokenSms(String loanStatusMessage) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String toPhoneNumber = "+905393909280";
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                loanStatusMessage
        ).create();
    }


    private void saveVerificationCode(SmsTokenRequest smsTokenRequest) {
        smsTokenService.save(smsTokenRequest);
    }

    @Override
    public String delete(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Loan not found with id: {}", id);
                    return new EntityNotFoundException("Loan not found with id: " + id);
                });

        loanRepository.delete(loan);
        logger.info("Loan deleted: {}", loan);
        return "Loan Deleted";
    }

    @Override
    public LoanResponseDto update(Long id, LoanRequestDto entity) {
        Loan existingLoan = loanRepository.findById((id))
                .orElseThrow(() -> {
                    logger.error("Loan not found with id: {}", id);
                    return new EntityNotFoundException("Loan not found with id: " + id);
                });
        Loan mappedToLoan = LoanMapper.mapToLoanAndLoanRequest(existingLoan, entity);
       /* existingLoan.setLoanType(LoanType.valueOf(entity.getLoanType()));
        existingLoan.setLoanAmount(entity.getLoanAmount());
        existingLoan.setInterestRate(entity.getInterestRate());
        existingLoan.setPaymentStart(entity.getPaymentStart());
        existingLoan.setPaymentEnd(entity.getPaymentEnd());
        existingLoan.setLoanDurationMonths(entity.getLoanDurationMonths());
        existingLoan.setRemainingAmount(existingLoan.getLoanAmount() - entity.getLoanAmount());
        if (existingLoan.getLoanAmount() == 0) {
            existingLoan.setLoanStatus(LoanStatus.CLOSED);
            existingLoan.getCustomer().setCreditScore(2000);
        }*/

        Loan updatedLoan = loanRepository.save(mappedToLoan);

        logger.info("Loan updated: {}", updatedLoan);
        return LoanMapper.mapToLoanResponse(updatedLoan);
    }

    public List<LoanInformationResponseDto> getLoanInformation() {
        return new LoanMapper().mapToLoanInformation();
    }

    public ResponseEntity<byte[]> createApprovalMessagePdf(LoanRequestDto savedLoan) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Başlık
            String title = "Tebrikler! Krediniz onaylandi";
            document.add(new Paragraph(title).setTextAlignment(TextAlignment.CENTER));

            // Kredi Detayları Tablosu
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            List<String> headers = Arrays.asList("Özellik", "Deger");
            headers.forEach(header -> {
                table.addCell(new Cell().add(new Paragraph(header)));
            });

            table.addCell(new Cell().add(new Paragraph("Kredi Miktarı")));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(savedLoan.getLoanAmount()))));

            table.addCell(new Cell().add(new Paragraph("Vade (Ay)")));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(savedLoan.getLoanDurationMonths()))));

            table.addCell(new Cell().add(new Paragraph("Durum")));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(LoanStatus.APPROVED))));

            table.addCell(new Cell().add(new Paragraph("Ödeme Baslangic Tarih")));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(savedLoan.getPaymentStart()))));

            table.addCell(new Cell().add(new Paragraph("Ödeme Bitis Tarih")));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(savedLoan.getPaymentEnd()))));

            table.addCell(new Cell().add(new Paragraph("Faiz Orani")));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(savedLoan.getInterestRate()))));

            document.add(table);
            document.close();

            byte[] pdfBytes = baos.toByteArray();
            HttpHeaders headerss = new HttpHeaders();
            headerss.setContentType(MediaType.APPLICATION_PDF);
            headerss.setContentDispositionFormData("filename", "loan-approval.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headerss)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("PDF oluşturulurken hata oluştu", e);
        }
    }
}
