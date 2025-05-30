package com.sms;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        System.out.println("Default timezone set to Asia/Kolkata");
    }
}
