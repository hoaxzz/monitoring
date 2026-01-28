package com.monitoringanak.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerkembanganAspekDTO {
    private Integer idAspek;
    private Integer idAnak;
    private LocalDate tanggal;
    private Integer agamaMoral;
    private Integer fisikMotorik;
    private Integer kognitif;
    private Integer bahasa;
    private Integer sosialEmosional;
    private Integer seni;
    private String catatan;
}
