package com.javaacademy.atomic.reactor_departament;

import com.javaacademy.atomic.security_departament.SecurityDepartment;
import com.javaacademy.atomic.exception.NuclearFuelsEmptyException;
import com.javaacademy.atomic.exception.ReactorWorkException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * Реакторный цех
 */
@Component
@Slf4j
public class ReactorDepartament {
    private static final BigDecimal AMOUNT_OF_ENERGY_GENERATED = BigDecimal.valueOf(10_000_000);
    private static final int DAY_NUMBER_FOR_TECHNICAL_WORKS = 100;
    private static final String WORKING_CONDITION = "Run";
    private static final String MESSAGE_WORKING_CONDITION = "Реактор уже работает!";
    private static final String NOT_IN_WORKING_CONDITION = "Not run";
    private static final String MESSAGE_NOT_IN_WORKING_CONDITION = "Реактор не работает!";
    private static final String MESSAGE_NO_FUEL = "В реакторе нет топлива";
    private Boolean isWorking = false;
    private static int counterReactorStart;
    @Autowired
    private SecurityDepartment securityDepartment;

//    public ReactorDepartament(SecurityDepartment securityDepartment) {
//        this.securityDepartment = securityDepartment;
//    }

    public BigDecimal run() throws ReactorWorkException {
        checkStateReactor(NOT_IN_WORKING_CONDITION);
        isWorking = true;
        counterReactorStart++;

        try {
            checkReactorStarts(counterReactorStart);
        } catch (NuclearFuelsEmptyException e) {
            log.info(e.getMessage());
            securityDepartment.addAccident();
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
            throw new ReactorWorkException(MESSAGE_WORKING_CONDITION);
        }

        if (state.equals(WORKING_CONDITION) && !isWorking) {
            throw new ReactorWorkException(MESSAGE_NOT_IN_WORKING_CONDITION);
        }
    }

    /**
     * Проверка количества запусков реактора
     */
    private void checkReactorStarts(int countStartsReactor) throws NuclearFuelsEmptyException {
        if (countStartsReactor % DAY_NUMBER_FOR_TECHNICAL_WORKS == 0) {
            throw new NuclearFuelsEmptyException(MESSAGE_NO_FUEL);
        }
    }
}
