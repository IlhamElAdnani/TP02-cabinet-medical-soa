package ma.fsr.soa.rendezvous.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.cabinetrepo.repository.RendezVousRepository;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    public RendezVousService(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    public List<RendezVous> getAll() {
        return rendezVousRepository.findAll();
    }

    public RendezVous getById(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable : id = " + id));
    }

    public List<RendezVous> getByPatientId(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId);
    }

    public List<RendezVous> getByMedecinId(Long medecinId) {
        return rendezVousRepository.findByMedecinId(medecinId);
    }

    public RendezVous create(RendezVous rdv) {

        if (rdv.getDate() == null)
            throw new RuntimeException("La date du rendez-vous est obligatoire.");

        if (rdv.getDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("La date du rendez-vous doit être future.");

        if (rdv.getPatientId() == null)
            throw new RuntimeException("Patient ID obligatoire.");

        if (rdv.getMedecinId() == null)
            throw new RuntimeException("Médecin ID obligatoire.");

        return rendezVousRepository.save(rdv);
    }

    public RendezVous update(Long id, RendezVous rdv) {
        RendezVous existing = getById(id);
        existing.setDate(rdv.getDate());
        existing.setPatientId(rdv.getPatientId());
        existing.setMedecinId(rdv.getMedecinId());
        return rendezVousRepository.save(existing);
    }

    public void delete(Long id) {
        rendezVousRepository.deleteById(id);
    }
}
