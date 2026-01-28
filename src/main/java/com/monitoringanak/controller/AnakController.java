package com.monitoringanak.controller;

import com.monitoringanak.model.Anak;
import com.monitoringanak.dto.ApiResponse;
import com.monitoringanak.service.AnakService;
import com.monitoringanak.security.RoleValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/anak")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AnakController {

    private final AnakService anakService;
    private final RoleValidator roleValidator;

    /**
     * Get all anak (filtered by role)
     * - Admin/Guru: Get all children
     * - Wali: Get only their children
     */
    @GetMapping
    public ResponseEntity<?> getAllAnak(HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            List<Anak> list;

            // Admin and Guru get all children
            if (roleValidator.hasFullAccess(userRole)) {
                list = anakService.getAllAnak();
            }
            // Wali only gets their own children
            else if (roleValidator.isWali(userRole)) {
                list = anakService.getAnakByWali(userId);
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
                            .message("Data anak retrieved successfully")
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
     * Get anak by ID (with role validation)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnakById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("userRole");

            // Validate access
            if (!roleValidator.userHasAccessToAnak(userId, userRole, id)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("You don't have permission to access this child's data")
                                .code(403)
                                .build());
            }

            Anak anak = anakService.getAnakById(id)
                    .orElseThrow(() -> new RuntimeException("Anak not found"));

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Anak retrieved successfully")
                            .data(anak)
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
     * Add/Create anak (Admin and Guru only)
     */
    @PostMapping
    public ResponseEntity<?> tambahAnak(@RequestBody Anak anak, HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can create children
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can create student records")
                                .code(403)
                                .build());
            }

            Anak created = anakService.tambahAnak(anak);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Anak created successfully")
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
     * Update anak (Admin and Guru only)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnak(@PathVariable Integer id, @RequestBody Anak details,
            HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can update children
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can update student records")
                                .code(403)
                                .build());
            }

            Anak updated = anakService.updateAnak(id, details);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Anak updated successfully")
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
     * Delete anak (Admin and Guru only)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnak(@PathVariable Integer id, HttpServletRequest request) {
        try {
            String userRole = (String) request.getAttribute("userRole");

            // Only Admin and Guru can delete children
            if (!roleValidator.hasFullAccess(userRole)) {
                return ResponseEntity.status(403).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Only admin and guru can delete student records")
                                .code(403)
                                .build());
            }

            anakService.deleteAnak(id);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Anak deleted successfully")
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
