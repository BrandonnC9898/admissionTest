package com.sprint3.admission_test.infrastructure.adapter.out.persistence.repositoryImpl;

import com.sprint3.admission_test.application.ports.out.IMedicationRepository;
import com.sprint3.admission_test.domain.model.Medication;
import com.sprint3.admission_test.infrastructure.adapter.out.persistence.jpaRepository.MedicationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicationRepositoryImpl implements IMedicationRepository {

    @Autowired
    private MedicationJpaRepository medicationJpaRepository;

    @Override
    public Optional<Medication> findById(Long id) {
        return medicationJpaRepository.findById(id);
    }

    public Medication save(Medication medication) {
        // TODO error handling
        return this.medicationJpaRepository.save(medication);
    }

    @Override
    public List<Medication> findByCategoryAndDateAfter(String category, LocalDate after) {
        Iterable<Medication> medications = medicationJpaRepository.findByCategoryAndDateAfter(category, after);
        List<Medication> result = new ArrayList<>();
        medications.forEach(medication -> result.add(medication));
        return result;
    }
}
