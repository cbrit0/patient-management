package com.cbrit0.patientservice.service;

import com.cbrit0.patientservice.dto.PatientRequestDTO;
import com.cbrit0.patientservice.dto.PatientResponseDTO;
import com.cbrit0.patientservice.exception.EmailAlreadyExistsException;
import com.cbrit0.patientservice.mapper.PatientMapper;
import com.cbrit0.patientservice.model.Patient;
import com.cbrit0.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists.");
        }

        Patient patient = PatientMapper.toModel(patientRequestDTO);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(savedPatient);
    }
}
