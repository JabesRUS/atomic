package com.javaacademy.atomic.atomic.reactor_departament;

import com.javaacademy.atomic.atomic.exception.NuclearFuelsEmptyException;
import com.javaacademy.atomic.atomic.exception.ReactorWorkException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Реакторный цех
 */

@Component
@Slf4j
public class ReactorDepartament {
    static final BigDecimal AMOUNT_OF_ENERGY_GENERATED = BigDecimal.valueOf(10_000_000);
    static final String WORKING_CONDITION = "Run";
    static final String NOT_IN_WORKING_CONDITION = "Not run";
    Boolean isWorking;
    static int counterReactorStart;

    public BigDecimal run() {
        counterReactorStart++;
        checkStateReactor(NOT_IN_WORKING_CONDITION);
        isWorking = true;
        counterReactorStart++;

        try {
            checkReactorStarts(counterReactorStart++);
        } catch (NuclearFuelsEmptyException e) {
            log.info("В реакторе нет топлива");
            return BigDecimal.ZERO;
        }
        return AMOUNT_OF_ENERGY_GENERATED;
    }

    public void stop() {
        checkStateReactor(WORKING_CONDITION);
        isWorking = true;
    }

    /**
     * Проверка состояния реактора(включен или нет)
     */
    private void checkStateReactor(@NonNull String state) {
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
        if(countStartsReactor % 100 == 0) {
            throw new NuclearFuelsEmptyException("В реакторе нет топлива");
        }
    }
}
