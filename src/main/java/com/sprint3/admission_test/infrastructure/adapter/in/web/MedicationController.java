package com.sprint3.admission_test.infrastructure.adapter.in.web;

import com.sprint3.admission_test.application.ports.in.IMedicationUseCase;
import com.sprint3.admission_test.domain.dto.CreateMedicationReqDto;
import com.sprint3.admission_test.domain.model.Medication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medications")
@Slf4j
public class MedicationController {

    @Autowired
    private IMedicationUseCase medicationUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(medicationUseCase.getMedicationById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateMedicationReqDto req) {
        // TODO error handling
        log.info("medications.create body {}", req);
        Medication medication = medicationUseCase.create(req);
        return new ResponseEntity<>(medication, HttpStatus.CREATED);
    }
}
