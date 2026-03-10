package com.testdata.repository;

import com.testdata.model.ssnPool;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SsnPoolRepository extends JpaRepository<ssnPool, String> {

    Optional<ssnPool> findFirstByUsedFalse();
}
