package com.example.ZeBank.Controller;

import com.example.ZeBank.EntityLayer.BaseEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface GenericController<T, R, D> {
//Customer T, CustomerRequestDto R, CustomerDtoResponse D

    ResponseEntity<List<D>> getAll();

    ResponseEntity<List<D>> getById(Long id);

    ResponseEntity<D> create(R entity);

    ResponseEntity<D> update(Long id, R entity);

    ResponseEntity<String> delete(Long id);
}