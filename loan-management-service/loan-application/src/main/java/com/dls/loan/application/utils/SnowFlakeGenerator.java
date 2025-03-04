package com.dls.loan.application.utils;

import org.springframework.stereotype.Component;

public class SnowFlakeGenerator {
    // 起始时间戳（可根据需要调整）
    private static final long START_STMP = 1480166465631L;

    // 各部分的位数
    private static final long SEQUENCE_BIT = 12;   // 序列号
    private static final long MACHINE_BIT = 5;     // 机器ID
    private static final long DATACENTER_BIT = 5;  // 数据中心ID

    // 各部分的左移位数
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    // 最大值（掩码）
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_BIT);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_BIT);

    // 成员变量
    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowFlakeGenerator(long datacenterId, long machineId) {
        validateId(datacenterId, MAX_DATACENTER_ID, "Datacenter ID");
        validateId(machineId, MAX_MACHINE_ID, "Machine ID");
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    private void validateId(long id, long maxId, String idName) {
        if (id < 0 || id > maxId) {
            throw new IllegalArgumentException(
                    idName + " must be between 0 and " + maxId);
        }
    }

    /**
     * 生成下一个ID（线程安全）
     */
    public synchronized long nextId() {
        long currentTimestamp = getCurrentTimestamp();
        validateTimestamp(currentTimestamp);

        // 同一毫秒内生成
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 当前毫秒序列号用完
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0L; // 新毫秒重置序列号
        }

        lastTimestamp = currentTimestamp;

        return composeId(currentTimestamp);
    }

    private long composeId(long timestamp) {
        return ((timestamp - START_STMP) << TIMESTAMP_LEFT)
                | (datacenterId << DATACENTER_LEFT)
                | (machineId << MACHINE_LEFT)
                | sequence;
    }

    private void validateTimestamp(long currentTimestamp) {
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException(
                    "Clock moved backwards. Refusing to generate id for "
                            + (lastTimestamp - currentTimestamp) + " milliseconds");
        }
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp <= lastTimestamp) {
            currentTimestamp = getCurrentTimestamp();
        }
        return currentTimestamp;
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

}