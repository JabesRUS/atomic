package com.javaacademy.atomic.atomic.config;

import com.javaacademy.atomic.atomic.reactor_departament.ReactorDepartament;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtomicStationConfig {

    @Bean("reactor_1")
    public ReactorDepartament reactorDepartament() {
        return new ReactorDepartament();
    }
}
