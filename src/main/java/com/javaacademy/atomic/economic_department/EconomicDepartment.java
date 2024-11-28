package com.javaacademy.atomic.economic_department;

import lombok.Getter;

import java.math.BigDecimal;

public abstract class EconomicDepartment {
    public abstract BigDecimal computeYearIncomes(long countElectricity);
}
