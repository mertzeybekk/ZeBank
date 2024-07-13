package com.example.ZeBank.Controller;

import com.example.ZeBank.Service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenericControllerImpl<T,R,D> implements GenericController<T,R,D> {

    private  final GenericService<T,R,D> genericService;

    public GenericControllerImpl(GenericService<T, R, D> genericService) {
        this.genericService = genericService;
    }


    @Override
    public ResponseEntity<List<D>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<List<D>> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<D> create(R entity) {
        return null;
    }

    @Override
    public ResponseEntity<D> update(Long id, R entity) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return null;
    }
}
