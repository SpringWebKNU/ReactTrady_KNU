package com.example.trady.service;

import com.example.trady.entity.Pcategory;
import com.example.trady.repository.PcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PcategoryServiceImpl implements PcategoryService {

    private final PcategoryRepository pcategoryRepository;

    @Autowired
    public PcategoryServiceImpl(PcategoryRepository pcategoryRepository) {
        this.pcategoryRepository = pcategoryRepository;
    }

    @Override
    @Transactional
    public Pcategory save(Pcategory pcategory) {
        return pcategoryRepository.save(pcategory);
    }

}