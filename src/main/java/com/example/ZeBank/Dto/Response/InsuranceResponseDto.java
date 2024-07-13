package com.example.ZeBank.Dto.Response;


public class InsuranceResponseDto {
    private Long id;
    private Long customerId;
    private Long accountId;
    private String type;

    public InsuranceResponseDto(Long id, Long customerId, Long accountId, String type) {
        this.id = id;
        this.customerId = customerId;
        this.accountId = accountId;
        this.type = type;
    }

    public InsuranceResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
