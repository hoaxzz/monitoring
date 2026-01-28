package com.monitoringanak.service;

import com.monitoringanak.model.Anak;
import com.monitoringanak.model.User;
import com.monitoringanak.repository.AnakRepository;
import com.monitoringanak.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class AnakService {

    private final AnakRepository anakRepository;
    private final UserRepository userRepository;

    /**
     * Create/Add anak
     */
    public Anak tambahAnak(Anak anak) {
        return anakRepository.save(anak);
    }

    /**
     * Get anak by ID
     */
    public Optional<Anak> getAnakById(Integer idAnak) {
        return anakRepository.findById(idAnak);
    }

    /**
     * Get all anak
     */
    public List<Anak> getAllAnak() {
        return anakRepository.findAll();
    }

    /**
     * Get all anak by guru
     */
    public List<Anak> getAnakByGuru(Integer idGuru) {
        return anakRepository.findByGuru_IdUser(idGuru);
    }

    /**
     * Get all anak by wali murid
     */
    public List<Anak> getAnakByWali(Integer idWali) {
        return anakRepository.findByWali_IdUser(idWali);
    }

    /**
     * Update anak
     */
    public Anak updateAnak(Integer idAnak, Anak anakDetails) {
        Anak anak = anakRepository.findById(idAnak)
                .orElseThrow(() -> new RuntimeException("Anak not found!"));

        if (anakDetails.getNamaAnak() != null) {
            anak.setNamaAnak(anakDetails.getNamaAnak());
        }
        if (anakDetails.getTglLahir() != null) {
            anak.setTglLahir(anakDetails.getTglLahir());
        }
        if (anakDetails.getJenisKelamin() != null) {
            anak.setJenisKelamin(anakDetails.getJenisKelamin());
        }
        if (anakDetails.getGuru() != null) {
            User guru = userRepository.findById(anakDetails.getGuru().getIdUser())
                    .orElseThrow(() -> new RuntimeException("Guru not found!"));
            anak.setGuru(guru);
        }
        if (anakDetails.getWali() != null) {
            User wali = userRepository.findById(anakDetails.getWali().getIdUser())
                    .orElseThrow(() -> new RuntimeException("Wali not found!"));
            anak.setWali(wali);
        }

        return anakRepository.save(anak);
    }

    /**
     * Delete anak
     */
    public void deleteAnak(Integer idAnak) {
        anakRepository.deleteById(idAnak);
    }
}
