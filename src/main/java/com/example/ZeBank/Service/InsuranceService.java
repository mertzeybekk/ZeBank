package com.example.ZeBank.Service;

import com.example.ZeBank.Dto.Request.InsuranceRequestDto;
import com.example.ZeBank.Dto.Response.InsuranceResponseDto;
import com.example.ZeBank.EntityLayer.Insurance;
import org.springframework.stereotype.Service;

@Service
public interface InsuranceService extends GenericService<Insurance, InsuranceRequestDto, InsuranceResponseDto> {
}
