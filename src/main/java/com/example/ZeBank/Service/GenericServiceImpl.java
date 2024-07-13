package com.example.ZeBank.Service;

import com.example.ZeBank.EntityLayer.BaseEntity;
import com.example.ZeBank.RepositoryLayer.BaseRepository;

import java.util.List;
import java.util.Optional;

public class GenericServiceImpl<T extends BaseEntity,D,R> implements GenericService<T,D,R> {

    private final BaseRepository<T> baseRepository;


    public GenericServiceImpl(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public List<R> findAll() {
        return List.of();
    }

    @Override
    public Optional<List<R>> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public R save(D entity) {
        return null;
    }

    @Override
    public String delete(Long id) {
      return "";
    }

    @Override
    public R update(Long Id, D entity) {
        return null;
    }
}

