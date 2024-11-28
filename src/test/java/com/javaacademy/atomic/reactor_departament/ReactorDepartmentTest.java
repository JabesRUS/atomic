package com.javaacademy.atomic.reactor_departament;

import com.javaacademy.atomic.nuclear_station.NuclearStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReactorDepartmentTest {
    @Autowired
    private ReactorDepartment reactorDepartment;

    @Test
    public void run() {
    }

    @Test
    public void stop() {
        reactorDepartment.stop();

    }
}