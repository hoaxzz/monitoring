package com.monitoringanak.repository;

import com.monitoringanak.model.Anak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnakRepository extends JpaRepository<Anak, Integer> {
    List<Anak> findByGuru_IdUser(Integer idGuru);
    List<Anak> findByWali_IdUser(Integer idWali);
}
