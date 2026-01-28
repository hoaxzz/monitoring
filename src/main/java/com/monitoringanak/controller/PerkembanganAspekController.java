package com.monitoringanak.controller;

import com.monitoringanak.model.PerkembanganAspek;
import com.monitoringanak.dto.ApiResponse;
import com.monitoringanak.service.PerkembanganAspekService;
import com.monitoringanak.security.RoleValidator;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/perkembangan/aspek")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class PerkembanganAspekController {

    private final PerkembanganAspekService perkembanganAspekService;
    private final RoleValidator roleValidator;

    /**
     * Get all perkembangan aspek for satu anak (with role validation)
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
                                .message("You don't have permission to access this child's development data")
                                .code(403)
                                .build());
            }

            List<PerkembanganAspek> list = perkembanganAspekService.getPerkembanganAspekByAnak(idAnak);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan aspek data retrieved successfully")
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
     * Get perkembangan aspek by date range (with role validation)
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
                                .message("You don't have permission to access this child's development data")
                                .code(403)
                                .build());
            }

            List<PerkembanganAspek> list = perkembanganAspekService.getPerkembanganAspekByAnakAndDateRange(idAnak,
                    startDate, endDate);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan aspek data retrieved successfully")
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
     * Get perkembangan aspek by ID (with role validation)
     */
    @GetMapping("/detail/{idAspek}")
    public ResponseEntity<?> getPerkembanganById(@PathVariable Integer idAspek, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            PerkembanganAspek perkembangan = perkembanganAspekService.getPerkembanganAspekById(idAspek)
                    .orElseThrow(() -> new RuntimeException("Perkembangan aspek not found"));

            // Validate access
            Integer idAnak = perkembangan.getAnak().getIdAnak();
            if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("You don't have permission to access this development data")
                                .code(403)
                                .build());
            }

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan aspek retrieved successfully")
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
     * Input/Add perkembangan aspek (Admin and Guru only)
     */
    @PostMapping
    public ResponseEntity<?> inputPerkembanganAspek(@RequestBody PerkembanganAspek perkembangan,
            HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can create development records
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can create development records")
                                .code(403)
                                .build());
            }

            PerkembanganAspek created = perkembanganAspekService.inputPerkembanganAspek(perkembangan);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan aspek created successfully")
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
     * Update perkembangan aspek (Admin and Guru only)
     */
    @PutMapping("/{idAspek}")
    public ResponseEntity<?> updatePerkembangan(@PathVariable Integer idAspek, @RequestBody PerkembanganAspek details,
            HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can update development records
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can update development records")
                                .code(403)
                                .build());
            }

            PerkembanganAspek updated = perkembanganAspekService.updatePerkembanganAspek(idAspek, details);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan aspek updated successfully")
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
     * Delete perkembangan aspek (Admin and Guru only)
     */
    @DeleteMapping("/{idAspek}")
    public ResponseEntity<?> deletePerkembangan(@PathVariable Integer idAspek, HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can delete development records
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can delete development records")
                                .code(403)
                                .build());
            }

            perkembanganAspekService.deletePerkembanganAspek(idAspek);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Perkembangan aspek deleted successfully")
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
     * Get average nilai for all aspects (with role validation)
     */
    @GetMapping("/{idAnak}/average")
    public ResponseEntity<?> getAverageAspek(@PathVariable Integer idAnak, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            // Validate access
            if (!roleValidator.userHasAccessToAnak(userId, userRole, idAnak)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("You don't have permission to access this child's development data")
                                .code(403)
                                .build());
            }

            Double average = perkembanganAspekService.calculateAverageAspek(idAnak);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Average aspek retrieved successfully")
                            .data(average)
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
