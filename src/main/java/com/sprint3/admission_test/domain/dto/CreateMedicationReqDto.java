package com.sprint3.admission_test.domain.dto;

import com.sprint3.admission_test.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMedicationReqDto {
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate expirationDate;
    private String categoryName;
}
