package com.example.ZeBank.Service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GenericService<T, D, R> {
    //customer(T),customerDTO(D),customerDtoRes(R);
    List<R> findAll();

    Optional<List<R>> findById(Long id);

    R save(D entity);

    String delete(Long id);

    R update(Long Id, D entity);
}