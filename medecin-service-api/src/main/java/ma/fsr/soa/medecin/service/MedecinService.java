package ma.fsr.soa.medecin.service;

import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.cabinetrepo.repository.MedecinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {

    private final MedecinRepository medecinRepository;

    public MedecinService(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    public Medecin getMedecinById(Long id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable : id = " + id));
    }

    public Medecin createMedecin(Medecin medecin) {
        if (medecin.getNom() == null || medecin.getNom().isEmpty())
            throw new RuntimeException("Le nom du médecin est obligatoire.");
        if (medecin.getEmail() == null || medecin.getEmail().isEmpty())
            throw new RuntimeException("L'email du médecin est obligatoire.");
        if (!medecin.getEmail().contains("@"))
            throw new RuntimeException("Email du médecin invalide.");
        if (medecin.getSpecialite() == null || medecin.getSpecialite().isEmpty())
            throw new RuntimeException("La spécialité du médecin est obligatoire.");

        return medecinRepository.save(medecin);
    }

    public Medecin updateMedecin(Long id, Medecin medecin) {
        Medecin existing = getMedecinById(id);
        existing.setNom(medecin.getNom());
        existing.setEmail(medecin.getEmail());
        existing.setSpecialite(medecin.getSpecialite());
        return medecinRepository.save(existing);
    }

    public void deleteMedecin(Long id) {
        medecinRepository.deleteById(id);
    }
}
