package com.sprint3.admission_test.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMedicationReqDto {

    @NotNull
    @Length(min = 5, max = 100)
    private String name;

    @NotNull
    @Length(min = 30, max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 12, fraction = 2)
    private BigDecimal price;

    private LocalDate expirationDate;

    @NotNull
    @Length(min = 3, max = 50)
    private String categoryName;
}
