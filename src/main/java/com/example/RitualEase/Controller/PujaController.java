package com.example.RitualEase.Controller;

import com.example.RitualEase.Service.PujaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PujaController {

    @Autowired
    private PujaService pujaService;

    // List all pujas
    @GetMapping("/pujas")
    public String listPujas(Model model) {
        model.addAttribute("pujas", pujaService.getAllPujas());
        return "pujas"; // pujas.html
    }

    @GetMapping("/panditsign/{id}")
    public String panditAssign(@PathVariable Long id, Model model){
        model.addAttribute("puja",pujaService.getPujaById(id));
        return "panditpuja";
    }

    // Show details of a single puja
    @GetMapping("/puja/{id}")
    public String pujaDetail(@PathVariable Long id, Model model) {
        model.addAttribute("puja", pujaService.getPujaById(id));
        return "pujadetaild"; // pujadetail.html
    }
}
