package com.monitoringanak.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaporanDTO {
    private Integer idLaporan;
    private Integer idAnak;
    private String namaAnak;
    private String periode;
    private String filePdf;
    private Integer idGuru;
    private String namaGuru;
    private LocalDateTime createdAt;
}
