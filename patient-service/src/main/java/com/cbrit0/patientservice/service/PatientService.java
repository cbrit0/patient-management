package com.cbrit0.patientservice.service;

import com.cbrit0.patientservice.dto.PatientRequestDTO;
import com.cbrit0.patientservice.dto.PatientResponseDTO;
import com.cbrit0.patientservice.exception.EmailAlreadyExistsException;
import com.cbrit0.patientservice.exception.PatientNotFoundException;
import com.cbrit0.patientservice.mapper.PatientMapper;
import com.cbrit0.patientservice.model.Patient;
import com.cbrit0.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient with id " + id + " not found.")
        );

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists.");
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setBirthDate(LocalDate.parse(patientRequestDTO.getBirthDate()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }
}
