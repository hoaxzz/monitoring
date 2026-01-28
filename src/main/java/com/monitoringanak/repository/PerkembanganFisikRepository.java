package com.monitoringanak.repository;

import com.monitoringanak.model.PerkembanganFisik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerkembanganFisikRepository extends JpaRepository<PerkembanganFisik, Integer> {
    List<PerkembanganFisik> findByAnak_IdAnak(Integer idAnak);
    List<PerkembanganFisik> findByAnak_IdAnakAndTanggalBetween(Integer idAnak, LocalDate startDate, LocalDate endDate);
}
