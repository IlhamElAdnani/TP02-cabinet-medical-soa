package ma.fsr.soa.consultation.service;

import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.cabinetrepo.repository.ConsultationRepository;
import ma.fsr.soa.cabinetrepo.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final RendezVousRepository rendezVousRepository;

    public ConsultationService(ConsultationRepository consultationRepository,
                               RendezVousRepository rendezVousRepository) {
        this.consultationRepository = consultationRepository;
        this.rendezVousRepository = rendezVousRepository;
    }

    public List<Consultation> getAll() {
        return consultationRepository.findAll();
    }

    public Consultation getById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Consultation introuvable : id = " + id));
    }

    public List<Consultation> getByRendezVousId(Long rdvId) {
        if (!rendezVousRepository.existsById(rdvId)) {
            throw new RuntimeException("Rendez-vous introuvable.");
        }
        return consultationRepository.findByRendezVousId(rdvId);
    }

    public Consultation create(Consultation consultation) {

        if (consultation.getRendezVousId() == null)
            throw new RuntimeException("Rendez-vous obligatoire.");

        if (!rendezVousRepository.existsById(consultation.getRendezVousId()))
            throw new RuntimeException("Rendez-vous introuvable.");

        if (consultation.getRapport() == null || consultation.getRapport().length() < 10)
            throw new RuntimeException("Rapport de consultation insuffisant.");

        return consultationRepository.save(consultation);
    }

    public Consultation update(Long id, Consultation consultation) {
        Consultation existing = getById(id);

        existing.setRapport(consultation.getRapport());
        existing.setRendezVousId(consultation.getRendezVousId());

        return consultationRepository.save(existing);
    }

    public void delete(Long id) {
        consultationRepository.deleteById(id);
    }
}
