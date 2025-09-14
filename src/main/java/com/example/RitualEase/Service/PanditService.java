package com.example.RitualEase.Service;
import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Repository.PanditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanditService {

    @Autowired
    private PanditRepository panditRepository;

    public List<Pandit> getAllPandits() {
        return panditRepository.findAll();
    }

    public Pandit getPanditById(Long id) {
        return panditRepository.findById(id).orElse(null);
    }

    public List<Pandit> getPanditsByPujaId(Long pujaId) {
        return panditRepository.findByPujas_Id(pujaId);
    }
}
