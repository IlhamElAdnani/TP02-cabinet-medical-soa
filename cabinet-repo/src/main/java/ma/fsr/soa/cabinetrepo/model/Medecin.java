package ma.fsr.soa.cabinetrepo.model;

import jakarta.persistence.*;

@Entity
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String specialite;

    // ===== getters =====
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialite() {
        return specialite;
    }

    // ===== setters =====
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
