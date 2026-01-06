package ma.fsr.soa.rendezvous.controller;

import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.rendezvous.service.RendezVousService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/rendezvous")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping
    public List<RendezVous> getAll() {
        return rendezVousService.getAll();
    }

    @GetMapping("/{id}")
    public RendezVous getById(@PathVariable Long id) {
        return rendezVousService.getById(id);
    }

    @GetMapping("/patient/{id}")
    public List<RendezVous> getByPatient(@PathVariable Long id) {
        return rendezVousService.getByPatientId(id);
    }

    @GetMapping("/medecin/{id}")
    public List<RendezVous> getByMedecin(@PathVariable Long id) {
        return rendezVousService.getByMedecinId(id);
    }

    @PostMapping
    public RendezVous create(@RequestBody RendezVous rdv) {
        return rendezVousService.create(rdv);
    }

    @PutMapping("/{id}")
    public RendezVous update(@PathVariable Long id, @RequestBody RendezVous rdv) {
        return rendezVousService.update(id, rdv);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rendezVousService.delete(id);
    }
}
