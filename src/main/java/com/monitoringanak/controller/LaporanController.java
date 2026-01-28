package com.monitoringanak.controller;

import com.monitoringanak.model.Laporan;
import com.monitoringanak.dto.ApiResponse;
import com.monitoringanak.service.LaporanService;
import com.monitoringanak.service.AnakService;
import com.monitoringanak.security.RoleValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/laporan")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class LaporanController {

    private final LaporanService laporanService;
    private final AnakService anakService;
    private final RoleValidator roleValidator;

    /**
     * Get all laporan (filtered by role)
     * - Admin/Guru: All reports
     * - Wali: Only reports for their children
     */
    @GetMapping
    public ResponseEntity<?> getAllLaporan(HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            List<Laporan> list;

            // Admin and Guru get all reports
            if (roleValidator.hasFullAccess(userRole)) {
                list = laporanService.getAllLaporan();
            }
            // Wali only gets reports for their children
            else if (roleValidator.isWali(userRole)) {
                // Get all wali's children IDs
                List<Integer> childrenIds = anakService.getAnakByWali(userId)
                        .stream()
                        .map(anak -> anak.getIdAnak())
                        .collect(Collectors.toList());

                // Get all reports and filter by children
                list = laporanService.getAllLaporan().stream()
                        .filter(laporan -> childrenIds.contains(laporan.getAnak().getIdAnak()))
                        .collect(Collectors.toList());
            } else {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Unauthorized access")
                                .code(403)
                                .build());
            }

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("All laporan retrieved successfully")
                            .data(list)
                            .code(200)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }

    /**
     * Get laporan by ID (with role validation)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLaporanById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            Laporan laporan = laporanService.getLaporanById(id)
                    .orElseThrow(() -> new RuntimeException("Laporan not found"));

            // Validate access for wali
            if (roleValidator.isWali(userRole)) {
                Integer idAnak = laporan.getAnak().getIdAnak();
                if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                    return ResponseEntity.status(403).body(
                            ApiResponse.builder()
                                    .success(false)
                                    .message("You don't have permission to access this report")
                                    .code(403)
                                    .build());
                }
            }

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Laporan retrieved successfully")
                            .data(laporan)
                            .code(200)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }

    /**
     * Get laporan by anak (with role validation)
     */
    @GetMapping("/anak/{idAnak}")
    public ResponseEntity<?> getLaporanByAnak(@PathVariable Integer idAnak, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            // Validate access
            if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("You don't have permission to access this child's reports")
                                .code(403)
                                .build());
            }

            List<Laporan> list = laporanService.getLaporanByAnak(idAnak);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Laporan for anak retrieved successfully")
                            .data(list)
                            .code(200)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }

    /**
     * Create laporan (Admin and Guru only)
     */
    @PostMapping
    public ResponseEntity<?> createLaporan(@RequestBody Laporan laporan, HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can create reports
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can create reports")
                                .code(403)
                                .build());
            }

            Laporan created = laporanService.createLaporan(laporan);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Laporan created successfully")
                            .data(created)
                            .code(200)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }

    /**
     * Update laporan (Admin and Guru only)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLaporan(@PathVariable Integer id, @RequestBody Laporan details,
            HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can update reports
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can update reports")
                                .code(403)
                                .build());
            }

            Laporan updated = laporanService.updateLaporan(id, details);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Laporan updated successfully")
                            .data(updated)
                            .code(200)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }

    /**
     * Delete laporan (Admin and Guru only)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLaporan(@PathVariable Integer id, HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can delete reports
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can delete reports")
                                .code(403)
                                .build());
            }

            laporanService.deleteLaporan(id);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Laporan deleted successfully")
                            .code(200)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }

    /**
     * Download laporan file (with role validation)
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            Laporan laporan = laporanService.getLaporanById(id)
                    .orElseThrow(() -> new RuntimeException("Laporan not found"));

            // Validate access for wali
            if (roleValidator.isWali(userRole)) {
                Integer idAnak = laporan.getAnak().getIdAnak();
                if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                    throw new RuntimeException("You don't have permission to download this report");
                }
            }

            String fileName = laporan.getFilePdf();
            if (fileName == null || fileName.isEmpty()) {
                throw new RuntimeException("File not found for this laporan");
            }

            Resource resource = laporanService.loadLaporanFile(fileName);

            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                // logger.info("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
