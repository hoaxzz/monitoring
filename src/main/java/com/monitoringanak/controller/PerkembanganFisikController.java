package com.monitoringanak.controller;

import com.monitoringanak.model.PerkembanganFisik;
import com.monitoringanak.dto.ApiResponse;
import com.monitoringanak.service.PerkembanganFisikService;
import com.monitoringanak.security.RoleValidator;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/perkembangan/fisik")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class PerkembanganFisikController {

    private final PerkembanganFisikService perkembanganFisikService;
    private final RoleValidator roleValidator;

    /**
     * Get all perkembangan fisik for satu anak (with role validation)
     */
    @GetMapping("/{idAnak}")
    public ResponseEntity<?> getPerkembanganByAnak(@PathVariable Integer idAnak, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            // Validate access
            if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("You don't have permission to access this child's physical development data")
                                .code(403)
                                .build());
            }

            List<PerkembanganFisik> list = perkembanganFisikService.getPerkembanganFisikByAnak(idAnak);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan fisik data retrieved successfully")
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
     * Get perkembangan fisik by date range (with role validation)
     */
    @GetMapping("/{idAnak}/range")
    public ResponseEntity<?> getPerkembanganByAnakAndDateRange(
            @PathVariable Integer idAnak,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            // Validate access
            if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("You don't have permission to access this child's physical development data")
                                .code(403)
                                .build());
            }

            List<PerkembanganFisik> list = perkembanganFisikService.getPerkembanganFisikByAnakAndDateRange(idAnak,
                    startDate, endDate);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan fisik data retrieved successfully")
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
     * Get perkembangan fisik by ID (with role validation)
     */
    @GetMapping("/detail/{idFisik}")
    public ResponseEntity<?> getPerkembanganById(@PathVariable Integer idFisik, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            PerkembanganFisik perkembangan = perkembanganFisikService.getPerkembanganFisikById(idFisik)
                    .orElseThrow(() -> new RuntimeException("Perkembangan fisik not found"));

            // Validate access
            Integer idAnak = perkembangan.getAnak().getIdAnak();
            if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("You don't have permission to access this physical development data")
                                .code(403)
                                .build());
            }

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan fisik retrieved successfully")
                            .data(perkembangan)
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
     * Input/Add perkembangan fisik (Admin and Guru only)
     */
    @PostMapping
    public ResponseEntity<?> inputPerkembanganFisik(@RequestBody PerkembanganFisik perkembangan,
            HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can create physical development records
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can create physical development records")
                                .code(403)
                                .build());
            }

            PerkembanganFisik created = perkembanganFisikService.inputPerkembanganFisik(perkembangan);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan fisik created successfully")
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
     * Update perkembangan fisik (Admin and Guru only)
     */
    @PutMapping("/{idFisik}")
    public ResponseEntity<?> updatePerkembangan(@PathVariable Integer idFisik, @RequestBody PerkembanganFisik details,
            HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can update physical development records
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can update physical development records")
                                .code(403)
                                .build());
            }

            PerkembanganFisik updated = perkembanganFisikService.updatePerkembanganFisik(idFisik, details);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan fisik updated successfully")
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
     * Delete perkembangan fisik (Admin and Guru only)
     */
    @DeleteMapping("/{idFisik}")
    public ResponseEntity<?> deletePerkembangan(@PathVariable Integer idFisik, HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can delete physical development records
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can delete physical development records")
                                .code(403)
                                .build());
            }

            perkembanganFisikService.deletePerkembanganFisik(idFisik);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan fisik deleted successfully")
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
}
