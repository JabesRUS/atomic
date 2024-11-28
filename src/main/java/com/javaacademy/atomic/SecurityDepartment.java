package com.javaacademy.atomic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityDepartment {
    private int accidentCountPeriod;
//    @Lazy
    @Autowired
    private NuclearStation nuclearStation;

//    public SecurityDepartment(@Lazy NuclearStation nuclearStation) {
//        this.nuclearStation = nuclearStation;
//    }

    /**
     * Добавление 1 инцидента
     */
    public void addAccident() {
        accidentCountPeriod++;
    }

    /**
     * Получаем количество инцидентов
     */
    public int getCountAccident() {
        return accidentCountPeriod;
    }

    /**
     * Передает инциденты в Атомную станцию и текущие обнуляет
     */
    public void reset() {
        nuclearStation.incrementAccident(accidentCountPeriod);
        accidentCountPeriod = 0;
    }
}
