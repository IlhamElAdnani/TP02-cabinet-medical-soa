package ma.fsr.soa.cabinetrepo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rapport;

    private Long rendezVousId;

    // ===== getters =====
    public Long getId() {
        return id;
    }

    public String getRapport() {
        return rapport;
    }

    public Long getRendezVousId() {
        return rendezVousId;
    }

    // ===== setters =====
    public void setId(Long id) {
        this.id = id;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public void setRendezVousId(Long rendezVousId) {
        this.rendezVousId = rendezVousId;
    }
}
