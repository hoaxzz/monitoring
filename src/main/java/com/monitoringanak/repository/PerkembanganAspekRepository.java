package com.monitoringanak.repository;

import com.monitoringanak.model.PerkembanganAspek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerkembanganAspekRepository extends JpaRepository<PerkembanganAspek, Integer> {
    List<PerkembanganAspek> findByAnak_IdAnak(Integer idAnak);
    List<PerkembanganAspek> findByAnak_IdAnakAndTanggalBetween(Integer idAnak, LocalDate startDate, LocalDate endDate);
}
