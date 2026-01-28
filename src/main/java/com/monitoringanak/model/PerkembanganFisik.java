package com.monitoringanak.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "perkembangan_fisik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerkembanganFisik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFisik;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anak", nullable = false)
    private Anak anak;

    @Column(nullable = false)
    private LocalDate tanggal;

    @Column(name = "tinggi_badan")
    private Float tinggiBadan;

    @Column(name = "berat_badan")
    private Float beratBadan;

    @Column(name = "lingkar_kepala")
    private Float lingkarKepala;

    @Column(name = "usia_bulan")
    private Integer usiaBulan;

    @Column(columnDefinition = "TEXT")
    private String catatan;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
