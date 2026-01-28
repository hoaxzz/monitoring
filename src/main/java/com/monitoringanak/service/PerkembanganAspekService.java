package com.monitoringanak.service;

import com.monitoringanak.model.PerkembanganAspek;
import com.monitoringanak.repository.PerkembanganAspekRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class PerkembanganAspekService {

    private final PerkembanganAspekRepository perkembanganAspekRepository;

    /**
     * Input/Add perkembangan aspek (6 aspek perkembangan)
     */
    public PerkembanganAspek inputPerkembanganAspek(PerkembanganAspek perkembangan) {
        return perkembanganAspekRepository.save(perkembangan);
    }

    /**
     * Get perkembangan aspek by ID
     */
    public Optional<PerkembanganAspek> getPerkembanganAspekById(Integer idAspek) {
        return perkembanganAspekRepository.findById(idAspek);
    }

    /**
     * Get semua perkembangan aspek untuk satu anak
     */
    public List<PerkembanganAspek> getPerkembanganAspekByAnak(Integer idAnak) {
        return perkembanganAspekRepository.findByAnak_IdAnak(idAnak);
    }

    /**
     * Get perkembangan aspek dalam range tanggal
     */
    public List<PerkembanganAspek> getPerkembanganAspekByAnakAndDateRange(Integer idAnak, LocalDate startDate,
            LocalDate endDate) {
        return perkembanganAspekRepository.findByAnak_IdAnakAndTanggalBetween(idAnak, startDate, endDate);
    }

    /**
     * Update perkembangan aspek
     */
    public PerkembanganAspek updatePerkembanganAspek(Integer idAspek, PerkembanganAspek details) {
        PerkembanganAspek perkembangan = perkembanganAspekRepository.findById(idAspek)
                .orElseThrow(() -> new RuntimeException("Perkembangan aspek not found!"));

        if (details.getTanggal() != null) {
            perkembangan.setTanggal(details.getTanggal());
        }
        if (details.getAgamaMoral() != null) {
            perkembangan.setAgamaMoral(details.getAgamaMoral());
        }
        if (details.getFisikMotorik() != null) {
            perkembangan.setFisikMotorik(details.getFisikMotorik());
        }
        if (details.getKognitif() != null) {
            perkembangan.setKognitif(details.getKognitif());
        }
        if (details.getBahasa() != null) {
            perkembangan.setBahasa(details.getBahasa());
        }
        if (details.getSosialEmosional() != null) {
            perkembangan.setSosialEmosional(details.getSosialEmosional());
        }
        if (details.getSeni() != null) {
            perkembangan.setSeni(details.getSeni());
        }
        if (details.getCatatan() != null) {
            perkembangan.setCatatan(details.getCatatan());
        }

        return perkembanganAspekRepository.save(perkembangan);
    }

    /**
     * Delete perkembangan aspek
     */
    public void deletePerkembanganAspek(Integer idAspek) {
        perkembanganAspekRepository.deleteById(idAspek);
    }

    /**
     * Calculate average nilai for all aspects
     */
    public Double calculateAverageAspek(Integer idAnak) {
        List<PerkembanganAspek> list = getPerkembanganAspekByAnak(idAnak);
        if (list.isEmpty())
            return 0.0;

        double sum = 0;
        int count = 0;
        for (PerkembanganAspek p : list) {
            sum += p.getAgamaMoral() + p.getFisikMotorik() + p.getKognitif()
                    + p.getBahasa() + p.getSosialEmosional() + p.getSeni();
            count += 6;
        }

        return count > 0 ? sum / count : 0.0;
    }
}
