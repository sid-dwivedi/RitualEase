package com.example.RitualEase.Service;
import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Entity.PanditPuja;
import com.example.RitualEase.Repository.PanditPujaRepository;
import com.example.RitualEase.Repository.PanditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PanditService {

    @Autowired
    private PanditRepository panditRepository;
    @Autowired
    private PanditPujaRepository panditPujaRepository;

    public List<Pandit> getAllPandits() {
        return panditRepository.findAll();
    }

    public Pandit getPanditById(Long id) {
        return panditRepository.findById(id).orElse(null);
    }

    public List<Pandit> getPanditsByPujaId(Long pujaId) {
        return panditRepository.findByPujas_Id(pujaId);
    }

    public List<Pandit> getPanditsByPujaAndLocation(Long pujaId, double userLat, double userLon, double radiusKm) {
        List<PanditPuja> allPanditPujas = panditPujaRepository.findByPujaId(pujaId);
        List<Pandit> result = new ArrayList<>();

        for(PanditPuja pp : allPanditPujas) {
            double distance = calculateDistance(userLat, userLon, pp.getLatitude(), pp.getLongitude());
            if(distance <= radiusKm) {
                result.add(pp.getPandit());
            }
        }
        return result;
    }

    // Haversine formula to calculate distance
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
