package com.javaacademy.atomic.economic_department;

import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Profile("france")
@EnableConfigurationProperties(value = EconomicOfCountryProperty.class)
public class FranceEconomicDepartment extends EconomicDepartment {
    private final EconomicOfCountryProperty countryProperty;

    public FranceEconomicDepartment(EconomicOfCountryProperty economicOfCountryProperty) {
        this.countryProperty = economicOfCountryProperty;
    }

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal rate = countryProperty.getRate();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal quota = countryProperty.getQuota();
        BigDecimal totalElectricity = BigDecimal.valueOf(countElectricity);
        BigDecimal countBillion = totalElectricity.divide(quota, RoundingMode.FLOOR);

        for (int i = 0; i <= countBillion.intValue(); i++) {
            BigDecimal currentIncome = rate.multiply(BigDecimal.valueOf(Math.pow(countryProperty.getDuty().doubleValue(), i)));
            totalIncome = totalIncome.add(quota.multiply(currentIncome));
        }

        return totalIncome;

    }

}
