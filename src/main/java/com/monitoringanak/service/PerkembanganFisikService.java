package com.monitoringanak.service;

import com.monitoringanak.model.PerkembanganFisik;
import com.monitoringanak.repository.PerkembanganFisikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class PerkembanganFisikService {

    private final PerkembanganFisikRepository perkembanganFisikRepository;

    /**
     * Input/Add perkembangan fisik
     */
    public PerkembanganFisik inputPerkembanganFisik(PerkembanganFisik perkembangan) {
        return perkembanganFisikRepository.save(perkembangan);
    }

    /**
     * Get perkembangan fisik by ID
     */
    public Optional<PerkembanganFisik> getPerkembanganFisikById(Integer idFisik) {
        return perkembanganFisikRepository.findById(idFisik);
    }

    /**
     * Get semua perkembangan fisik untuk satu anak
     */
    public List<PerkembanganFisik> getPerkembanganFisikByAnak(Integer idAnak) {
        return perkembanganFisikRepository.findByAnak_IdAnak(idAnak);
    }

    /**
     * Get perkembangan fisik dalam range tanggal
     */
    public List<PerkembanganFisik> getPerkembanganFisikByAnakAndDateRange(Integer idAnak, LocalDate startDate,
            LocalDate endDate) {
        return perkembanganFisikRepository.findByAnak_IdAnakAndTanggalBetween(idAnak, startDate, endDate);
    }

    /**
     * Update perkembangan fisik
     */
    public PerkembanganFisik updatePerkembanganFisik(Integer idFisik, PerkembanganFisik details) {
        PerkembanganFisik perkembangan = perkembanganFisikRepository.findById(idFisik)
                .orElseThrow(() -> new RuntimeException("Perkembangan fisik not found!"));

        if (details.getTanggal() != null) {
            perkembangan.setTanggal(details.getTanggal());
        }
        if (details.getTinggiBadan() != null) {
            perkembangan.setTinggiBadan(details.getTinggiBadan());
        }
        if (details.getBeratBadan() != null) {
            perkembangan.setBeratBadan(details.getBeratBadan());
        }
        if (details.getLingkarKepala() != null) {
            perkembangan.setLingkarKepala(details.getLingkarKepala());
        }
        if (details.getUsiaBulan() != null) {
            perkembangan.setUsiaBulan(details.getUsiaBulan());
        }
        if (details.getCatatan() != null) {
            perkembangan.setCatatan(details.getCatatan());
        }

        return perkembanganFisikRepository.save(perkembangan);
    }

    /**
     * Delete perkembangan fisik
     */
    public void deletePerkembanganFisik(Integer idFisik) {
        perkembanganFisikRepository.deleteById(idFisik);
    }
}
