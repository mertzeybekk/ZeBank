package com.example.ZeBank.Dto.Request;

import com.example.ZeBank.EntityLayer.Enum.InsuranceType;

public class InsuranceRequestDto {
    private Long customerId;
    private Long accountId;
    private String type;

    public InsuranceRequestDto(Long customerId, Long accountId, String type) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.type = type;
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
