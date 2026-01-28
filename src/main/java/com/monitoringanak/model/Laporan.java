package com.monitoringanak.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "laporan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laporan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLaporan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anak", nullable = false)
    private Anak anak;

    @Column(length = 50)
    private String periode;

    @Column(name = "file_pdf", length = 255)
    private String filePdf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dibuat_oleh", nullable = false)
    private User dibuatOleh;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
