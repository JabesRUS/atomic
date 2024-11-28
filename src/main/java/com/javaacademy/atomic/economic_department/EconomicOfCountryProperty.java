package com.javaacademy.atomic.economic_department;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.math.BigDecimal;

@ConfigurationProperties(prefix = "")
@Data
public class EconomicOfCountryProperty {
    private String country;
    private BigDecimal rate;
    private BigDecimal increasedRate;
    private String currency;
    private BigDecimal quota;
    private BigDecimal duty;

}
