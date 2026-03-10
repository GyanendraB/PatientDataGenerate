package com.testdata.repository;

import com.testdata.model.SsnPool;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SsnPoolRepository extends JpaRepository<SsnPool, String> {

    Optional<SsnPool> findFirstByUsedFalse();

    Optional<SsnPool> findTopByOrderBySsnDesc();
}
