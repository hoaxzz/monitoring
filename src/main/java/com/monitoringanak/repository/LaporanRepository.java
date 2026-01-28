package com.monitoringanak.repository;

import com.monitoringanak.model.Laporan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LaporanRepository extends JpaRepository<Laporan, Integer> {
    List<Laporan> findByAnak_IdAnak(Integer idAnak);
    List<Laporan> findByDibuatOleh_IdUser(Integer idUser);
}
