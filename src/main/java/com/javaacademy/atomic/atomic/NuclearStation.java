package com.javaacademy.atomic.atomic;

import com.javaacademy.atomic.atomic.reactor_departament.ReactorDepartament;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@Slf4j
public class NuclearStation {
    static final String NUCLEAR_POWER_PLANT_LAUNCH_MESSAGE = "Атомная станция начала работу";
    static final String NUCLEAR_POWER_PLANT_SHUTDOWN_MESSAGE = "Атомная станция закончила работу.\n" +
            " За год выработано {} киловатт/часов.";
    static final String MESSAGE_OF_TECHNICAL_WORK = "Внимание! Происходят технические работы. Электричества нет!";
    ReactorDepartament reactorDepartament;
    BigDecimal amountEnergyGenerated;

    public NuclearStation(ReactorDepartament reactorDepartament, BigDecimal amountEnergyGenerated) {
        this.reactorDepartament = reactorDepartament;
        this.amountEnergyGenerated = BigDecimal.ZERO;
    }

    private void startYear() {
        log.info(NUCLEAR_POWER_PLANT_LAUNCH_MESSAGE);
        for (int i = 1; i <= 365; i++) {
            BigDecimal amountEnergyFromDay = reactorDepartament.run();
            if (Objects.equals(amountEnergyFromDay, BigDecimal.ZERO)) {
                log.info(MESSAGE_OF_TECHNICAL_WORK);
            }
            amountEnergyGenerated = amountEnergyGenerated.add(amountEnergyFromDay);
        }

        log.info(NUCLEAR_POWER_PLANT_SHUTDOWN_MESSAGE, amountEnergyGenerated);
    }

    public void start(int year) {
        for (int i = 0; i < year; i++) {
            startYear();
        }
    }
}
