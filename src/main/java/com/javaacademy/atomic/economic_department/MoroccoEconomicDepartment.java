package com.javaacademy.atomic.economic_department;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("morocco")
@EnableConfigurationProperties(value = EconomicOfCountryProperty.class)
@Slf4j
public class MoroccoEconomicDepartment extends EconomicDepartment {
    private final EconomicOfCountryProperty countryProperty;

    public MoroccoEconomicDepartment(EconomicOfCountryProperty countryProperty) {
        this.countryProperty = countryProperty;
    }

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal rate = countryProperty.getRate();
        BigDecimal increasedRate = countryProperty.getIncreasedRate();
        BigDecimal quota = countryProperty.getQuota();
        BigDecimal totalElectricity = BigDecimal.valueOf(countElectricity);

        // Часть по базовой цене
        BigDecimal baseIncome = totalElectricity.min(quota).multiply(rate);

        // Часть по увеличенной цене
        BigDecimal additionalElectricity = totalElectricity.subtract(quota).max(BigDecimal.ZERO);
        BigDecimal additionalIncome = additionalElectricity.multiply(increasedRate);

        // Общий доход
        return baseIncome.add(additionalIncome);
    }
}
