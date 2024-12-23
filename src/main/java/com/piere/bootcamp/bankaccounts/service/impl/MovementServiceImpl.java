package com.piere.bootcamp.bankaccounts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piere.bootcamp.bankaccounts.dao.MovementDao;
import com.piere.bootcamp.bankaccounts.model.dto.MovementDto;
import com.piere.bootcamp.bankaccounts.service.MovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementDao movementDao;

    @Override
    public Mono<MovementDto> create(MovementDto movementDto) {
        return movementDao.save(MovementDto.build().toEntity(movementDto))
            .map(MovementDto.build()::toDto);
    }

    @Override
    public Mono<MovementDto> update(MovementDto movementDto) {
        return movementDao.save(MovementDto.build().toEntity(movementDto))
            .map(MovementDto.build()::toDto);
    }

    @Override
    public Mono<Void> delete(MovementDto movementDto) {
        return movementDao.delete(MovementDto.build().toEntity(movementDto));
    }

    @Override
    public Flux<MovementDto> findAll() {
        return movementDao.findAll()
            .map(MovementDto.build()::toDto);
    }

    @Override
    public Flux<MovementDto> findAllByIdList(List<String> idList) {
        return movementDao.findAllById(idList)
            .map(MovementDto.build()::toDto);
    }
}
