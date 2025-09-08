package com.example.RitualEase.Service;

import com.example.RitualEase.Entity.Puja;
import com.example.RitualEase.Repository.PujaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PujaService {
    @Autowired
    private PujaRepository pujaRepository;

    public List<Puja>getAllPujas(){
        return pujaRepository.findAll();
    }
    public Puja getPujaById(Long id){
        return pujaRepository.findById(id).orElseThrow(()-> new RuntimeException("Puja Not Found By Id -"+id));
    }
    public Puja createPuja(Puja puja){
        return pujaRepository.save(puja);
    }
    public void deletePuja(Long id){
        Puja puja=getPujaById(id);
        pujaRepository.delete(puja);
    }
    public Puja updatePuja(Long id,Puja pujaDetails){
        Puja puja=getPujaById(id);
        puja.setPujaName(pujaDetails.getPujaName());
        puja.setPujaDescription(pujaDetails.getPujaDescription());
        puja.setPrice(pujaDetails.getPrice());
        return pujaRepository.save(puja);
    }
}
