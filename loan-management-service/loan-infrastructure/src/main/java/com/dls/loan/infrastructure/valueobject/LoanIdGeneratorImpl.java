package com.dls.loan.infrastructure.valueobject;

import com.dls.loan.application.utils.SnowFlakeGenerator;
import com.dls.loan.domain.core.valueobject.LoanIdGenerator;
import org.springframework.stereotype.Component;

@Component
public class LoanIdGeneratorImpl implements LoanIdGenerator {

    private final SnowFlakeGenerator snowFlakeGenerator;

    public LoanIdGeneratorImpl(SnowFlakeGenerator snowFlakeGenerator) {
        this.snowFlakeGenerator = snowFlakeGenerator;
    }

    @Override
    public String generate() {
        return String.valueOf(snowFlakeGenerator.nextId());
    }
}
