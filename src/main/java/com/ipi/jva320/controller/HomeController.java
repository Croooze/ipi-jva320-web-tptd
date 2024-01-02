package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.repository.SalarieAideADomicileRepository;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Autowired
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    @GetMapping("/")
    public String home(ModelMap modelMap) {
        long nombreSalaries = salarieAideADomicileService.countSalaries();
        modelMap.put("nombreSalaries", nombreSalaries);
        return "home";
    }

    @GetMapping("/salaries")
    public String listSalaries(ModelMap model) {
        List<SalarieAideADomicile> listeSalaries = salarieAideADomicileService.getSalaries();
        model.addAttribute("salaries", listeSalaries);
        return "list";
    }

    @GetMapping("/salaries/{id}")
    public String detail(ModelMap model, @PathVariable Long id) {
        SalarieAideADomicile detail = salarieAideADomicileService.getSalarie(id);
        if (detail != null) {
            model.put("salarie", detail);
            return "detail_Salarie";
        } else {
            return "redirect:/salaries";
        }
    }

    @GetMapping("/salaries/aide/new")
    public String newSalarie(ModelMap model) {
        List<SalarieAideADomicile> listeSalaries = salarieAideADomicileService.getSalaries();
        model.addAttribute("salaries", listeSalaries);
        return "detail_Salarie";
    }

    @PostMapping("/salaries/aide/{id}")
    public String saveSalarie(@ModelAttribute SalarieAideADomicile salarie) {
        salarieAideADomicileRepository.save(salarie);
        return "redirect:/salaries";
    }

    @GetMapping("/salaries/{id}/delete")
    public String supprimerSalarie(@PathVariable Long id) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(id);
        return "redirect:/salaries";
    }

    @GetMapping("/recherche-salaries")
    public String searchName(@RequestParam("nom") String nom, Long id, ModelMap modelMap) {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);

        if (salarie != null) {
            Long countSalaries = salarieAideADomicileService.countSalaries();
            modelMap.put("nombreSalaries", countSalaries);
            modelMap.put("salarie", salarie);
            return "detail_Salarie";
        } else {
            modelMap.put("erreurMessage", "Aucun salarié trouvé avec le nom : " + nom);
            return "erreur";
        }
    }

    @GetMapping("/salaries/{id}/modifier")
    public String afficherPageModification(@PathVariable Long id, ModelMap modelMap) {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        Long countSalaries = salarieAideADomicileService.countSalaries();
        modelMap.put("nombreSalaries", countSalaries);
        modelMap.put("salarie", salarie);
        return "detail_Salarie";
    }

    @PostMapping("/salaries/{id}/modifier")
    public String soumettreModification(@ModelAttribute SalarieAideADomicile salarieModifie) throws SalarieException {
        salarieAideADomicileService.updateSalarieAideADomicile(salarieModifie);
        return "redirect:/salaries";
    }
}
