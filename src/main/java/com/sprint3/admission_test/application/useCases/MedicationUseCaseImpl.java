package com.sprint3.admission_test.application.useCases;

import com.sprint3.admission_test.application.ports.in.IMedicationUseCase;
import com.sprint3.admission_test.application.ports.out.ICategoryRepository;
import com.sprint3.admission_test.application.ports.out.IMedicationRepository;
import com.sprint3.admission_test.domain.dto.CreateMedicationReqDto;
import com.sprint3.admission_test.domain.dto.FindAllMedicationsReqDto;
import com.sprint3.admission_test.domain.exceptions.NotFoundException;
import com.sprint3.admission_test.domain.model.Category;
import com.sprint3.admission_test.domain.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationUseCaseImpl implements IMedicationUseCase {

    @Autowired
    private IMedicationRepository medicationRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Could not find medication with ID: " + id
        ));
    }

    @Override
    public Medication create(CreateMedicationReqDto req) {
        // TODO error handling
        Optional<Category> categoryOpt = categoryRepository.findByName(req.getCategoryName());
        if (categoryOpt.isEmpty()) {
            // TODO trhow custom exception
            throw new NotFoundException("Category with name " + req.getCategoryName() + " not found");
        }
        Medication medication = Medication.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .expirationDate(req.getExpirationDate())
                .category(categoryOpt.get())
                .build();
        this.medicationRepository.save(medication);
        return medication;
    }

    @Override
    public List<Medication> getAllMedications(FindAllMedicationsReqDto req) {
        return medicationRepository.findByCategoryAndDateAfter(req.getCategoryName(),
                req.getExpAfter());
    }
}
