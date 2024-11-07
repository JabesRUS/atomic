package com.javaacademy.atomic.atomic.reactor_departament;

import com.javaacademy.atomic.atomic.exception.NuclearFuelsEmptyException;
import com.javaacademy.atomic.atomic.exception.ReactorWorkException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Реакторный цех
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactorDepartament {
    static final BigDecimal AMOUNT_OF_ENERGY_GENERATED = BigDecimal.valueOf(10_000_000);
    static final int DAY_NUMBER_FOR_TECHNICAL_WORKS = 100;
    static final String WORKING_CONDITION = "Run";
    static final String NOT_IN_WORKING_CONDITION = "Not run";
    Boolean isWorking = false;
    static int counterReactorStart;

    public BigDecimal run() {
        try {
            checkStateReactor(NOT_IN_WORKING_CONDITION);
            isWorking = true;
            counterReactorStart++;
        } catch (ReactorWorkException e) {
            log.info(e.getMessage());
        }
//        isWorking = true;
//        counterReactorStart++;

        try {
            checkReactorStarts(counterReactorStart);
        } catch (NuclearFuelsEmptyException e) {
            log.info("В реакторе нет топлива");
            return BigDecimal.ZERO;
        }
        return AMOUNT_OF_ENERGY_GENERATED;
    }

    public void stop() {
        try {
            checkStateReactor(WORKING_CONDITION);
            isWorking = false;
        } catch (ReactorWorkException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Проверка состояния реактора(включен или нет)
     */
    private void checkStateReactor(@NonNull String state) throws ReactorWorkException {
        if (state.equals(NOT_IN_WORKING_CONDITION) && isWorking) {
            throw new ReactorWorkException("Реактор уже работает!");
        }

        if (state.equals(WORKING_CONDITION) && !isWorking) {
            throw new ReactorWorkException("Реактор не работает!");
        }
    }

    /**
     * Проверка количества запусков реактора
     */
    private void checkReactorStarts(int countStartsReactor) throws NuclearFuelsEmptyException {
        if (countStartsReactor % DAY_NUMBER_FOR_TECHNICAL_WORKS == 0) {
            throw new NuclearFuelsEmptyException("В реакторе нет топлива");
        }
    }
}
