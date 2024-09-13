package com.sprint3.admission_test.application.ports.in;

import com.sprint3.admission_test.domain.dto.CreateMedicationReqDto;
import com.sprint3.admission_test.domain.dto.FindAllMedicationsReqDto;
import com.sprint3.admission_test.domain.model.Medication;

import java.util.List;

public interface IMedicationUseCase {
    Medication getMedicationById(Long id);
    Medication create(CreateMedicationReqDto req);
    List<Medication> getAllMedications(FindAllMedicationsReqDto req);
}
