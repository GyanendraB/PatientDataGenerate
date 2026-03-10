package com.testdata.service;

import com.testdata.model.SsnPool;
import com.testdata.repository.SsnPoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SsnAllocationService {

    private final SsnPoolRepository ssnPoolRepository;

    public SsnAllocationService(SsnPoolRepository ssnPoolRepository) {
        this.ssnPoolRepository = ssnPoolRepository;
    }

    @Transactional
    public String getAvailableSSN() {
        SsnPool ssn = ssnPoolRepository.findFirstByUsedFalse()
                .orElseThrow(() -> new IllegalStateException("No available SSN in pool"));

        ssn.setUsed(true);
        ssnPoolRepository.save(ssn);
        return ssn.getSsn();
    }

    @Transactional
    public void markAllUnused() {
        ssnPoolRepository.findAll().forEach(item -> item.setUsed(false));
    }
}
