package com.monitoringanak.security;

import com.monitoringanak.model.Anak;
import com.monitoringanak.repository.AnakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleValidator {

    private final AnakRepository anakRepository;

    /**
     * Check if user is admin
     */
    public boolean isAdmin(String role) {
        return "admin".equalsIgnoreCase(role);
    }

    /**
     * Check if user is guru (teacher)
     */
    public boolean isGuru(String role) {
        return "guru".equalsIgnoreCase(role);
    }

    /**
     * Check if user is wali murid (guardian)
     */
    public boolean isWali(String role) {
        return "wali_murid".equalsIgnoreCase(role);
    }

    /**
     * Check if user has admin or guru role (full access)
     */
    public boolean hasFullAccess(String role) {
        return isAdmin(role) || isGuru(role);
    }

    /**
     * Validate if wali (guardian) has access to a specific child
     */
    public boolean waliHasAccessToAnak(Integer idWali, Integer idAnak) {
        Optional<Anak> anak = anakRepository.findById(idAnak);
        return anak.isPresent() &&
                anak.get().getWali() != null &&
                anak.get().getWali().getIdUser().equals(idWali);
    }

    /**
     * Validate if user has access to a specific child based on role
     */
    public boolean userHasAccessToAnak(Integer userId, String role, Integer idAnak) {
        // Admin and Guru have full access
        if (hasFullAccess(role)) {
            return true;
        }

        // Wali can only access their own children
        if (isWali(role)) {
            return waliHasAccessToAnak(userId, idAnak);
        }

        return false;
    }

    /**
     * Get list of child IDs that a wali has access to
     */
    public boolean validateWaliAccess(Integer userId, String role) {
        return isWali(role);
    }
}
