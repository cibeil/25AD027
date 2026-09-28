package com.example.attendance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AttendanceConfig {
    private final double minimumPercentage;

    public AttendanceConfig(@Value("${attendance.minimum-percentage:75}") double minimumPercentage) {
        if (minimumPercentage < 0 || minimumPercentage > 100) {
            throw new IllegalArgumentException("attendance.minimum-percentage must be between 0 and 100");
        }
        this.minimumPercentage = minimumPercentage;
    }

    public double getMinimumPercentage() {
        return minimumPercentage;
    }
}
