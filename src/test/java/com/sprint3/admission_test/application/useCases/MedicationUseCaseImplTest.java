package com.sprint3.admission_test.application.useCases;

import com.sprint3.admission_test.application.ports.out.ICategoryRepository;
import com.sprint3.admission_test.application.ports.out.IMedicationRepository;
import com.sprint3.admission_test.domain.dto.CreateMedicationReqDto;
import com.sprint3.admission_test.domain.exceptions.NotFoundException;
import com.sprint3.admission_test.domain.model.Category;
import com.sprint3.admission_test.domain.model.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MedicationUseCaseImplTest {

    @MockBean
    private IMedicationRepository medicationRepository;

    @MockBean
    private ICategoryRepository categoryRepository;

    @Autowired
    private MedicationUseCaseImpl medicationUseCase;

    @Test
    public void whenCallGetMedicationById_ShouldReturnMedication() {
        Category category = new Category(1L, "Analgesics");
        Medication medication = new Medication(
                1L,
                "Aspirin",
                "Used to reduce pain, fever, or inflammation.",
                new BigDecimal("5.99"),
                LocalDate.of(2024, 1, 1),
                category
        );

        when(medicationRepository.findById(1L)).thenReturn(
                Optional.of(medication)
        );
        assertEquals(medication, this.medicationUseCase.getMedicationById(1L));
    }

    @Test
    public void whenCallGetMedicationById_ShouldReturnNotFoundExp() {
        when(medicationRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> this.medicationUseCase.getMedicationById(100L));
    }

    @Test
    public void whenCallCreate_ShouldReturnMedication() {
        CreateMedicationReqDto req = new CreateMedicationReqDto(
                "Aspirin",
                "Used to reduce pain, fever, or inflammation.",
                new BigDecimal("5.99"),
                LocalDate.of(2026, 1, 1),
                "Analgesics"
        );
        Category category = new Category(1L, "Analgesics");
        Medication medication = Medication.builder()
                .id(1L)
                .name("Aspirin")
                .description("Used to reduce pain, fever, or inflammation.")
                .price(new BigDecimal("5.99"))
                .expirationDate(LocalDate.of(2026, 1, 1))
                .category(category)
                .build();
        when(categoryRepository.findByName("Analgesics")).thenReturn(Optional.of(category));
        when(medicationRepository.save(medication)).thenReturn(medication);
        assertEquals(medication, medicationUseCase.create(req));
    }
}
