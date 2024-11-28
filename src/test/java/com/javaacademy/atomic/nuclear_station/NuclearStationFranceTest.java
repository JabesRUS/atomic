package com.javaacademy.atomic.nuclear_station;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("france")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NuclearStationFranceTest {
    @Autowired
    @SpyBean
    private NuclearStation nuclearStation;

    @ParameterizedTest
    @CsvFileSource(resources = "/dataFromIncrementAccidentMethod.csv", numLinesToSkip = 1, delimiter = ';')
    public void addIncrementAccidentSuccess(int addCount, int expected) {

        nuclearStation.incrementAccident(addCount);

        int beforeAdd = nuclearStation.getAccidentCountAllTime();
        Assertions.assertEquals(expected, beforeAdd);
    }

    @Test
    public void startNotException() {

    }

}