package com.testdata.service;

import com.testdata.model.SsnPool;
import com.testdata.repository.SsnPoolRepository;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SsnAllocationService {

    private static final String SSN_FORMAT = "%03d-%02d-%04d";
    private static final Pattern SSN_PATTERN = Pattern.compile("(\\d{3})-(\\d{2})-(\\d{4})");

    private final SsnPoolRepository ssnPoolRepository;

    public SsnAllocationService(SsnPoolRepository ssnPoolRepository) {
        this.ssnPoolRepository = ssnPoolRepository;
    }

    @Transactional
    public String getAvailableSSN() {
        SsnPool ssn = ssnPoolRepository.findFirstByUsedFalse()
                .orElseGet(this::createAndReserveNextSsn);

        if (!ssn.isUsed()) {
            ssn.setUsed(true);
            ssnPoolRepository.save(ssn);
        }
        return ssn.getSsn();
    }

    private SsnPool createAndReserveNextSsn() {
        String nextSsn = ssnPoolRepository.findTopByOrderBySsnDesc()
                .map(SsnPool::getSsn)
                .map(this::incrementSsn)
                .orElse(formatSsn(900, 11, 1));

        SsnPool generatedSsn = new SsnPool(nextSsn, true);
        return ssnPoolRepository.save(generatedSsn);
    }

    private String incrementSsn(String ssn) {
        Matcher matcher = SSN_PATTERN.matcher(ssn);
        if (!matcher.matches()) {
            throw new IllegalStateException("Invalid SSN format in pool: " + ssn);
        }

        int part1 = Integer.parseInt(matcher.group(1));
        int part2 = Integer.parseInt(matcher.group(2));
        int part3 = Integer.parseInt(matcher.group(3));

        part3++;
        if (part3 > 9999) {
            part3 = 0;
            part2++;
        }
        if (part2 > 99) {
            part2 = 0;
            part1++;
        }
        if (part1 > 999) {
            throw new IllegalStateException("SSN pool exhausted");
        }

        return formatSsn(part1, part2, part3);
    }

    private String formatSsn(int part1, int part2, int part3) {
        return String.format(SSN_FORMAT, part1, part2, part3);
    }

    @Transactional
    public void markAllUnused() {
        ssnPoolRepository.findAll().forEach(item -> item.setUsed(false));
    }

    @Transactional
    public void markUnused(List<String> ssns) {
        if (ssns.isEmpty()) {
            return;
        }
        List<SsnPool> records = ssnPoolRepository.findAllById(ssns);
        records.forEach(record -> record.setUsed(false));
        ssnPoolRepository.saveAll(records);
    }

    public long total() {
        return ssnPoolRepository.count();
    }

    public long used() {
        return ssnPoolRepository.countByUsed(true);
    }
}
