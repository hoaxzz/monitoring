package com.monitoringanak.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnakDTO {
    private Integer idAnak;
    private String namaAnak;
    private LocalDate tglLahir;
    private String jenisKelamin;
    private Integer idGuru;
    private String namaGuru;
    private Integer idWali;
    private String namaWali;
}
