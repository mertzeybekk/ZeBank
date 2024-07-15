package com.example.ZeBank.EntityLayer;

import com.twilio.twiml.voice.Sms;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "smsToken")
@Data
public class SmsToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "code", nullable = false)
    private String code;

    public SmsToken(String username, String code) {
        this.username = username;
        this.code = code;
    }

    public SmsToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
