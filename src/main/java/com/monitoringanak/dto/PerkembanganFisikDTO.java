package com.monitoringanak.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerkembanganFisikDTO {
    private Integer idFisik;
    private Integer idAnak;
    private LocalDate tanggal;
    private Float tinggiBadan;
    private Float beratBadan;
    private Float lingkarKepala;
    private Integer usiaBulan;
    private String catatan;
}
