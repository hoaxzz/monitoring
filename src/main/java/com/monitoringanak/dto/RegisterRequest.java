package com.monitoringanak.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String nama;
    private String email;
    private Integer idRole;
}
