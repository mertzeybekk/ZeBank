package com.example.ZeBank.Dto.Request;

public class SmsTokenRequest {
    private String username;
    private String code;

    public SmsTokenRequest(String username, String code) {
        this.username = username;
        this.code = code;
    }

    public SmsTokenRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
