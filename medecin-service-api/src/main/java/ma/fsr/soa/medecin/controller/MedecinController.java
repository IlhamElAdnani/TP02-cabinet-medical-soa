package ma.fsr.soa.medecin.controller;

import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.medecin.service.MedecinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/medecins")
public class MedecinController {

    private final MedecinService medecinService;

    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    @GetMapping
    public List<Medecin> getAll() {
        return medecinService.getAllMedecins();
    }

    @GetMapping("/{id}")
    public Medecin getById(@PathVariable Long id) {
        return medecinService.getMedecinById(id);
    }

    @PostMapping
    public Medecin create(@RequestBody Medecin medecin) {
        return medecinService.createMedecin(medecin);
    }

    @PutMapping("/{id}")
    public Medecin update(@PathVariable Long id, @RequestBody Medecin medecin) {
        return medecinService.updateMedecin(id, medecin);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
    }
}
