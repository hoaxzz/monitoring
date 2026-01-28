package com.monitoringanak.service;

import com.monitoringanak.model.Laporan;
import com.monitoringanak.repository.LaporanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class LaporanService {

    private final LaporanRepository laporanRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Create/Add laporan
     */
    public Laporan createLaporan(Laporan laporan) {
        return laporanRepository.save(laporan);
    }

    /**
     * Get laporan by ID
     */
    public Optional<Laporan> getLaporanById(Integer idLaporan) {
        return laporanRepository.findById(idLaporan);
    }

    /**
     * Get semua laporan untuk satu anak
     */
    public List<Laporan> getLaporanByAnak(Integer idAnak) {
        return laporanRepository.findByAnak_IdAnak(idAnak);
    }

    /**
     * Get semua laporan yang dibuat oleh guru
     */
    public List<Laporan> getLaporanByGuru(Integer idGuru) {
        return laporanRepository.findByDibuatOleh_IdUser(idGuru);
    }

    /**
     * Get all laporan
     */
    public List<Laporan> getAllLaporan() {
        return laporanRepository.findAll();
    }

    /**
     * Update laporan
     */
    public Laporan updateLaporan(Integer idLaporan, Laporan details) {
        Laporan laporan = laporanRepository.findById(idLaporan)
                .orElseThrow(() -> new RuntimeException("Laporan not found!"));

        if (details.getPeriode() != null) {
            laporan.setPeriode(details.getPeriode());
        }
        if (details.getFilePdf() != null) {
            laporan.setFilePdf(details.getFilePdf());
        }

        return laporanRepository.save(laporan);
    }

    /**
     * Delete laporan
     */
    public void deleteLaporan(Integer idLaporan) {
        laporanRepository.deleteById(idLaporan);
    }

    /**
     * Load laporan file as Resource
     */
    public Resource loadLaporanFile(String filename) throws FileNotFoundException, MalformedURLException {
        Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found: " + filename);
        }
    }
}
