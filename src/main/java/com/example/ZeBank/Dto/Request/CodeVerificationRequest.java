package com.example.ZeBank.Dto.Request;

public class CodeVerificationRequest {
    private String username;
    private String verificationCode;

    public CodeVerificationRequest(String username, String verificationCode) {
        this.username = username;
        this.verificationCode = verificationCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
