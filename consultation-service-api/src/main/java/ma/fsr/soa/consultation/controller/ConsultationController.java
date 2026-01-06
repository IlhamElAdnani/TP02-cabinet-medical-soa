package ma.fsr.soa.consultation.controller;

import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.consultation.service.ConsultationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public List<Consultation> getAll() {
        return consultationService.getAll();
    }

    @GetMapping("/{id}")
    public Consultation getById(@PathVariable Long id) {
        return consultationService.getById(id);
    }

    @GetMapping("/rendezvous/{id}")
    public List<Consultation> getByRendezVous(@PathVariable Long id) {
        return consultationService.getByRendezVousId(id);
    }

    @PostMapping
    public Consultation create(@RequestBody Consultation consultation) {
        return consultationService.create(consultation);
    }

    @PutMapping("/{id}")
    public Consultation update(@PathVariable Long id, @RequestBody Consultation consultation) {
        return consultationService.update(id, consultation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        consultationService.delete(id);
    }
}
