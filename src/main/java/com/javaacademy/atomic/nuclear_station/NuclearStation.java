package com.javaacademy.atomic.nuclear_station;

import com.javaacademy.atomic.economic_department.EconomicDepartment;
import com.javaacademy.atomic.economic_department.EconomicOfCountryProperty;
import com.javaacademy.atomic.exception.ReactorWorkException;
import com.javaacademy.atomic.reactor_departament.ReactorDepartment;
import com.javaacademy.atomic.security_departament.SecurityDepartment;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Атомная станция
 */
@Component
@Slf4j
@EnableConfigurationProperties(value = EconomicOfCountryProperty.class)
public class NuclearStation {
    private static final String NUCLEAR_POWER_PLANT_LAUNCH_MESSAGE = "Атомная станция начала работу";
    private static final String NUCLEAR_POWER_PLANT_SHUTDOWN_MESSAGE = "Атомная станция закончила работу. " +
            "За год выработано {} киловатт/часов.";
    private static final String MESSAGE_OF_TECHNICAL_WORK = "Внимание! Происходят технические работы. Электричества нет!";
    private static final String INCIDENT_REPORTING_FOR_YEAR = "Количество инцидентов за год {}";
    private static final String INCIDENT_REPORTING_FOR_ENTIRE_PERIOD = "Количество инцидентов за всю работу станции {}";
    private static final int NUMBER_OF_DAYS_IN_YEAR = 365;
    private BigDecimal amountEnergyGenerated = BigDecimal.ZERO;
    @Getter
    private int accidentCountAllTime;
    @Lazy
    @Autowired
    private ReactorDepartment reactorDepartment;
    @Lazy
    @Autowired
    private SecurityDepartment securityDepartment;
    @Autowired
    private EconomicDepartment economicDepartment;
    private final EconomicOfCountryProperty countryProperty;

    public NuclearStation(EconomicOfCountryProperty economicOfCountryProperty) {
        this.countryProperty = economicOfCountryProperty;
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
                amountEnergyFromDay = reactorDepartment.run();
            } catch (ReactorWorkException e) {
                log.info(e.getMessage());
                System.exit(1);
            }

            if (amountEnergyFromDay.equals(BigDecimal.ZERO)) {
                log.info(MESSAGE_OF_TECHNICAL_WORK);
            }
            amountEnergyGeneratedYear = amountEnergyGeneratedYear.add(amountEnergyFromDay);
            reactorDepartment.stop();
        }

        amountEnergyGenerated = amountEnergyGenerated.add(amountEnergyGeneratedYear);
        log.info(NUCLEAR_POWER_PLANT_SHUTDOWN_MESSAGE, NumberFormat.getInstance().format(amountEnergyGeneratedYear));

        BigDecimal income = economicDepartment.computeYearIncomes(amountEnergyGeneratedYear.longValue());
        log.info("Доход за год составил - {} {}", NumberFormat.getInstance().format(income), countryProperty.getCurrency());

        log.info(INCIDENT_REPORTING_FOR_YEAR, securityDepartment.getCountAccident());
        securityDepartment.reset();
    }

    /**
     * Метод запуска реактора на указанное количество лет
     */
    public void start(int year) {
        log.info("Действие происходит в стране - {}", countryProperty.getCountry());
        for (int i = 0; i < year; i++) {
            startYear();
        }
        log.info(INCIDENT_REPORTING_FOR_ENTIRE_PERIOD, accidentCountAllTime);
    }

    /**
     * Метод изменения количества инцидентов
     */
    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }
}
