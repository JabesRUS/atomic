package com.javaacademy.atomic;

import com.javaacademy.atomic.exception.ReactorWorkException;
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
public class NuclearStation {
    private static final String NUCLEAR_POWER_PLANT_LAUNCH_MESSAGE = "Атомная станция начала работу";
    private static final String NUCLEAR_POWER_PLANT_SHUTDOWN_MESSAGE = "Атомная станция закончила работу. " +
            "За год выработано {} киловатт/часов.";
    private static final String MESSAGE_OF_TECHNICAL_WORK = "Внимание! Происходят технические работы. Электричества нет!";
    private static final int NUMBER_OF_DAYS_IN_YEAR = 365;
    private ReactorDepartament reactorDepartament;
    private BigDecimal amountEnergyGenerated = BigDecimal.ZERO;

    public NuclearStation(ReactorDepartament reactorDepartament) {
        this.reactorDepartament = reactorDepartament;
    }

    /**
     * Метод запуска реактора на 1 год
     */
    private void startYear() {
        BigDecimal amountEnergyGeneratedYear = BigDecimal.ZERO;
        log.info(NUCLEAR_POWER_PLANT_LAUNCH_MESSAGE);

        for (int i = 1; i <= NUMBER_OF_DAYS_IN_YEAR; i++) {
            BigDecimal amountEnergyFromDay = null;

            try {
                amountEnergyFromDay = reactorDepartament.run();
            } catch (ReactorWorkException e) {
                log.info(e.getMessage());
                System.exit(1);
            }

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
