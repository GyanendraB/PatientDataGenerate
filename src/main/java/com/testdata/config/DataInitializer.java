package com.testdata.config;

import com.testdata.model.SsnPool;
import com.testdata.repository.SsnPoolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSSNs(SsnPoolRepository repo) {
        return args -> {

            if(repo.count() == 0) {

                for(int i=1;i<=1000;i++) {

                    String ssn = String.format("900-11-%04d", i);

                    repo.save(new SsnPool(ssn,false));
                }

                System.out.println("Loaded 1000 SSNs into pool");
            }
        };
    }
}