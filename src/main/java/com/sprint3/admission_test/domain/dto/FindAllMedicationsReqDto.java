package com.sprint3.admission_test.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAllMedicationsReqDto {
    private String categoryName;
    private LocalDate expAfter;
}
