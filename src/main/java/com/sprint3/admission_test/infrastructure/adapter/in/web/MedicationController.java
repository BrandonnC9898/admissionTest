package com.sprint3.admission_test.infrastructure.adapter.in.web;

import com.sprint3.admission_test.application.ports.in.IMedicationUseCase;
import com.sprint3.admission_test.domain.dto.CreateMedicationReqDto;
import com.sprint3.admission_test.domain.dto.FindAllMedicationsReqDto;
import com.sprint3.admission_test.domain.exceptions.BadRequestException;
import com.sprint3.admission_test.domain.exceptions.NotFoundException;
import com.sprint3.admission_test.domain.model.Medication;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public ResponseEntity<?> create(@RequestBody @Valid CreateMedicationReqDto req) {
        try {
            log.info("medications.create body {}", req);
            Medication medication = medicationUseCase.create(req);
            return new ResponseEntity<>(medication, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getAllMedicationsByCategory(@PathVariable @NotNull
                                                         @Length(min = 3, max = 50) String category,
                                                         @RequestParam("expiration-after") String expAfter) {
        log.info("medications.medicationsByCategory category {} expAfter {}", category, expAfter);
        LocalDate expAfterDate = LocalDate.parse(expAfter);
        try {
            List<Medication> medications = medicationUseCase.getAllMedications(new FindAllMedicationsReqDto(category, expAfterDate));
            return new ResponseEntity<>(medications, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
