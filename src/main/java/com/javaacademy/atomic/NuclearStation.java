package com.javaacademy.atomic;

import com.javaacademy.atomic.reactor_departament.ReactorDepartament;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Атомная станция
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NuclearStation {
    static final String NUCLEAR_POWER_PLANT_LAUNCH_MESSAGE = "Атомная станция начала работу";
    static final String NUCLEAR_POWER_PLANT_SHUTDOWN_MESSAGE = "Атомная станция закончила работу. " +
            "За год выработано {} киловатт/часов.";
    static final String MESSAGE_OF_TECHNICAL_WORK = "Внимание! Происходят технические работы. Электричества нет!";
    static final int NUMBER_OF_DAYS_IN_YEAR = 365;
    ReactorDepartament reactorDepartament;
    BigDecimal amountEnergyGenerated;

    public NuclearStation(@Qualifier("reactor_1") ReactorDepartament reactorDepartament) {
        this.reactorDepartament = reactorDepartament;
        this.amountEnergyGenerated = BigDecimal.ZERO;
    }

    /**
     * Метод запуска реактора на 1 год
     */
    private void startYear() {
        BigDecimal amountEnergyGeneratedYear = BigDecimal.ZERO;
        log.info(NUCLEAR_POWER_PLANT_LAUNCH_MESSAGE);

        for (int i = 1; i <= NUMBER_OF_DAYS_IN_YEAR; i++) {
            BigDecimal amountEnergyFromDay = reactorDepartament.run();
            if (amountEnergyFromDay.equals(BigDecimal.ZERO)) {
                log.info(MESSAGE_OF_TECHNICAL_WORK);
            }
            amountEnergyGeneratedYear = amountEnergyGeneratedYear.add(amountEnergyFromDay);
            reactorDepartament.stop();
        }

        amountEnergyGenerated = amountEnergyGenerated.add(amountEnergyGeneratedYear);
        log.info(NUCLEAR_POWER_PLANT_SHUTDOWN_MESSAGE, NumberFormat.getInstance().format(amountEnergyGeneratedYear));
    }

    /**
     * Метод запуска реактора на указанное количество лет
     */
    public void start(int year) {
        for (int i = 0; i < year; i++) {
            startYear();
        }
    }
}
