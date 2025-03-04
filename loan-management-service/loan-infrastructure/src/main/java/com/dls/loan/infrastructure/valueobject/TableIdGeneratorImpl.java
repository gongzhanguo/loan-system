package com.dls.loan.infrastructure.valueobject;

import com.dls.loan.application.utils.SnowFlakeGenerator;
import com.dls.loan.domain.core.valueobject.TableIdGenerator;
import org.springframework.stereotype.Component;

@Component
public class TableIdGeneratorImpl implements TableIdGenerator {

    private final SnowFlakeGenerator snowFlakeGenerator;

    public TableIdGeneratorImpl(SnowFlakeGenerator snowFlakeGenerator) {
        this.snowFlakeGenerator = snowFlakeGenerator;
    }

    @Override
    public String generate() {
        return String.valueOf(snowFlakeGenerator.nextId());
    }
}
