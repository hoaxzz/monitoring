package com.monitoringanak.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "anak")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnak;

    @Column(nullable = false, length = 100)
    private String namaAnak;

    @Column(name = "tgl_lahir")
    private LocalDate tglLahir;

    @Column(length = 10)
    private String jenisKelamin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_guru")
    private User guru;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_wali")
    private User wali;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
