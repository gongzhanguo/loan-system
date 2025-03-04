package com.dls.loan.application.config;

import com.dls.loan.application.utils.SnowFlakeGenerator;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "snow-flake")
public class SnowFlakConfiguration {

    private String machineId;
    private String datacenterId;

    @Bean
    public SnowFlakeGenerator snowFlakeGenerator() {
        if (datacenterId == null || machineId == null) {
            throw new IllegalArgumentException("datacenterId and machineId must be set");
        }
        return new SnowFlakeGenerator(Long.parseLong(datacenterId), Long.parseLong(machineId));
    }
}
