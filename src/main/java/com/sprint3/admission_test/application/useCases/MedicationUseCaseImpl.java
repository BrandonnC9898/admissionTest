package com.sprint3.admission_test.application.useCases;

import com.sprint3.admission_test.application.ports.in.IMedicationUseCase;
import com.sprint3.admission_test.application.ports.out.ICategoryRepository;
import com.sprint3.admission_test.application.ports.out.IMedicationRepository;
import com.sprint3.admission_test.domain.dto.CreateMedicationReqDto;
import com.sprint3.admission_test.domain.dto.FindAllMedicationsReqDto;
import com.sprint3.admission_test.domain.exceptions.BadRequestException;
import com.sprint3.admission_test.domain.exceptions.NotFoundException;
import com.sprint3.admission_test.domain.model.Category;
import com.sprint3.admission_test.domain.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Optional<Category> categoryOpt = categoryRepository.findByName(req.getCategoryName());
        if (categoryOpt.isEmpty()) {
            throw new NotFoundException("Category with name " + req.getCategoryName() + " not found");
        }
        if (!this.validateExpirationDate(req.getExpirationDate())) {
            throw new BadRequestException("Invalid expiration date");
        };
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
        Optional<Category> categoryOpt = categoryRepository.findByName(req.getCategoryName());
        if (categoryOpt.isEmpty()) {
            throw new NotFoundException("Category with name " + req.getCategoryName() + " not found");
        }
        return medicationRepository.findByCategoryAndDateAfter(req.getCategoryName(),
                req.getExpAfter());
    }

    // Private methods
    private boolean validateExpirationDate(LocalDate expirationDate) {
        LocalDate now = LocalDate.now();
        return expirationDate.isAfter(now);
    }
}
