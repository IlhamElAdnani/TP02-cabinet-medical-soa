package ma.fsr.soa.patient.service;

import ma.fsr.soa.cabinetrepo.model.Patient;
import ma.fsr.soa.cabinetrepo.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable : id = " + id));
    }

    public Patient createPatient(Patient patient) {
        if (patient.getNom() == null || patient.getNom().isEmpty()) {
            throw new RuntimeException("Le nom du patient est obligatoire.");
        }
        if (patient.getTelephone() == null || patient.getTelephone().isEmpty()) {
            throw new RuntimeException("Le téléphone du patient est obligatoire.");
        }
        if (patient.getDateNaissance() != null && patient.getDateNaissance().isAfter(LocalDate.now())) {
            throw new RuntimeException("La date de naissance ne peut pas être future.");
        }
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient patient) {
        Patient existing = getPatientById(id);
        existing.setNom(patient.getNom());
        existing.setTelephone(patient.getTelephone());
        existing.setDateNaissance(patient.getDateNaissance());
        return patientRepository.save(existing);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
