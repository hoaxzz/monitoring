package com.monitoringanak.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Integer idUser;
    private String username;
    private String nama;
    private String role;
    private String token;
    private String message;
}
