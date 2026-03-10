package com.testdata.config;

import com.testdata.model.SsnPool;
import com.testdata.repository.SsnPoolRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SsnPoolDataLoader implements CommandLineRunner {

    private final SsnPoolRepository ssnPoolRepository;

    public SsnPoolDataLoader(SsnPoolRepository ssnPoolRepository) {
        this.ssnPoolRepository = ssnPoolRepository;
    }

    @Override
    public void run(String... args) {
        if (ssnPoolRepository.count() > 0) {
            return;
        }

        List<String> defaultSsns = List.of("900-11-0001", "900-11-0002", "900-11-0003");
        defaultSsns.forEach(value -> {
            SsnPool pool = new SsnPool();
            pool.setSsn(value);
            pool.setUsed(false);
            ssnPoolRepository.save(pool);
        });
    }
}
