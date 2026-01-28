package com.monitoringanak.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "perkembangan_aspek")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerkembanganAspek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAspek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anak", nullable = false)
    private Anak anak;

    @Column(nullable = false)
    private LocalDate tanggal;

    @Column(name = "agama_moral")
    private Integer agamaMoral;

    @Column(name = "fisik_motorik")
    private Integer fisikMotorik;

    @Column(nullable = false)
    private Integer kognitif;

    @Column(nullable = false)
    private Integer bahasa;

    @Column(name = "sosial_emosional")
    private Integer sosialEmosional;

    @Column(nullable = false)
    private Integer seni;

    @Column(columnDefinition = "TEXT")
    private String catatan;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
