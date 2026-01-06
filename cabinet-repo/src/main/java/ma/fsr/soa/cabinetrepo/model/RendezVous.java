package ma.fsr.soa.cabinetrepo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private Long patientId;
    private Long medecinId;

    // ===== getters =====
    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    // ===== setters =====
    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }
}
