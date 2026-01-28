-- Database: monitoring_anak
-- Sistem Monitoring Perkembangan Anak (TK/PAUD)

-- Drop tables in reverse order of dependencies
DROP TABLE IF EXISTS laporan;
DROP TABLE IF EXISTS perkembangan_aspek;
DROP TABLE IF EXISTS perkembangan_fisik;
DROP TABLE IF EXISTS anak;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- ============================================================================
-- TABEL ROLES
-- ============================================================================
CREATE TABLE roles (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    nama_role VARCHAR(50) NOT NULL UNIQUE
);

-- Insert default roles
INSERT INTO roles (nama_role) VALUES ('admin');
INSERT INTO roles (nama_role) VALUES ('guru');
INSERT INTO roles (nama_role) VALUES ('wali_murid');

-- ============================================================================
-- TABEL USERS
-- ============================================================================
CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    id_role INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_role) REFERENCES roles(id_role) ON DELETE RESTRICT
);

-- ============================================================================
-- TABEL ANAK
-- ============================================================================
CREATE TABLE anak (
    id_anak INT AUTO_INCREMENT PRIMARY KEY,
    nama_anak VARCHAR(100) NOT NULL,
    tgl_lahir DATE,
    jenis_kelamin VARCHAR(10),
    id_guru INT,
    id_wali INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_guru) REFERENCES users(id_user) ON DELETE SET NULL,
    FOREIGN KEY (id_wali) REFERENCES users(id_user) ON DELETE SET NULL
);

-- ============================================================================
-- TABEL PERKEMBANGAN FISIK
-- ============================================================================
CREATE TABLE perkembangan_fisik (
    id_fisik INT AUTO_INCREMENT PRIMARY KEY,
    id_anak INT NOT NULL,
    tanggal DATE NOT NULL,
    tinggi_badan FLOAT,
    berat_badan FLOAT,
    lingkar_kepala FLOAT,
    usia_bulan INT,
    catatan TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_anak) REFERENCES anak(id_anak) ON DELETE CASCADE
);

-- ============================================================================
-- TABEL PERKEMBANGAN ASPEK
-- ============================================================================
CREATE TABLE perkembangan_aspek (
    id_aspek INT AUTO_INCREMENT PRIMARY KEY,
    id_anak INT NOT NULL,
    tanggal DATE NOT NULL,
    agama_moral INT CHECK (agama_moral >= 0 AND agama_moral <= 100),
    fisik_motorik INT CHECK (fisik_motorik >= 0 AND fisik_motorik <= 100),
    kognitif INT CHECK (kognitif >= 0 AND kognitif <= 100),
    bahasa INT CHECK (bahasa >= 0 AND bahasa <= 100),
    sosial_emosional INT CHECK (sosial_emosional >= 0 AND sosial_emosional <= 100),
    seni INT CHECK (seni >= 0 AND seni <= 100),
    catatan TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_anak) REFERENCES anak(id_anak) ON DELETE CASCADE
);

-- ============================================================================
-- TABEL LAPORAN
-- ============================================================================
CREATE TABLE laporan (
    id_laporan INT AUTO_INCREMENT PRIMARY KEY,
    id_anak INT NOT NULL,
    periode VARCHAR(50),
    file_laporan LONGBLOB,
    file_name VARCHAR(255),
    dibuat_oleh INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_anak) REFERENCES anak(id_anak) ON DELETE CASCADE,
    FOREIGN KEY (dibuat_oleh) REFERENCES users(id_user) ON DELETE RESTRICT
);



-- ============================================================================
-- INDEX untuk optimasi query
-- ============================================================================
CREATE INDEX idx_users_id_role ON users(id_role);
CREATE INDEX idx_anak_id_guru ON anak(id_guru);
CREATE INDEX idx_anak_id_wali ON anak(id_wali);
CREATE INDEX idx_perkembangan_fisik_id_anak ON perkembangan_fisik(id_anak);
CREATE INDEX idx_perkembangan_aspek_id_anak ON perkembangan_aspek(id_anak);
CREATE INDEX idx_laporan_id_anak ON laporan(id_anak);
CREATE INDEX idx_laporan_dibuat_oleh ON laporan(dibuat_oleh);


-- ============================================================================
-- DATA DUMMY
-- ============================================================================

-- ============================================================================
-- PASSWORD DOCUMENTATION
-- ============================================================================
-- Admin users: password = 'admin123' -> $2a$10$5F8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7
-- Guru users:  password = 'guru123'  -> $2a$10$N.5F8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L
-- Wali users:  password = 'wali123'  -> $2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N

-- 1. Insert Users (2 Admin, 1 Guru, 29 Wali Murid)
INSERT INTO users (id_user, username, password, nama, email, id_role) VALUES 
(1, 'admin', '$2a$10$5F8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7', 'Administrator', 'admin@sekolah.com', 1),
(2, 'admin_sekolah', '$2a$10$5F8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7', 'Admin Sekolah', 'admin.sekolah@sekolah.com', 1),
(3, 'guru_budi', '$2a$10$N.5F8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L', 'Budi Santoso', 'budi@sekolah.com', 2),
(4, 'wali_azzam', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Ahmad Azzam', 'wali.azzam@email.com', 3),
(5, 'wali_rafif', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Ahmad Rafif', 'wali.rafif@email.com', 3),
(6, 'wali_ani', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Al Syafa Anirah', 'wali.ani@email.com', 3),
(7, 'wali_alesha', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Alesha Putri', 'wali.alesha@email.com', 3),
(8, 'wali_apri', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Apri Yansa', 'wali.apri@email.com', 3),
(9, 'wali_arafah', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Arafah Almahyra', 'wali.arafah@email.com', 3),
(10, 'wali_arsyila', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Arsyila Ratifah', 'wali.arsyila@email.com', 3),
(11, 'wali_azkiya', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Azkiya Yumna', 'wali.azkiya@email.com', 3),
(12, 'wali_cassanova', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Cassanova Nisa', 'wali.cassanova@email.com', 3),
(13, 'wali_clara', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Clara Salsabila', 'wali.clara@email.com', 3),
(14, 'wali_dafian', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Dafiansyah', 'wali.dafiansyah@email.com', 3),
(15, 'wali_hafizh', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Hafizh Aryaguna', 'wali.hafizh@email.com', 3),
(16, 'wali_nusa', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali M Nusa Saputra', 'wali.nusa@email.com', 3),
(17, 'wali_razka', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali M Raffa Azka', 'wali.razka@email.com', 3),
(18, 'wali_amar', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali M Amar Athala', 'wali.amar@email.com', 3),
(19, 'wali_abil', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Muhammad Abil', 'wali.abil@email.com', 3),
(20, 'wali_aji', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Muhammad Aji', 'wali.aji@email.com', 3),
(21, 'wali_mahdi', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Muhammad Al Mahdi', 'wali.mahdi@email.com', 3),
(22, 'wali_rezky', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Muhammad Rezky', 'wali.rezky@email.com', 3),
(23, 'wali_noureen', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Noureen Mikayla', 'wali.noureen@email.com', 3),
(24, 'wali_rafif_s', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Rafif Shidqi', 'wali.rafif_s@email.com', 3),
(25, 'wali_rafka', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Rafka Arsha', 'wali.rafka@email.com', 3),
(26, 'wali_rania', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Rania Putri', 'wali.rania@email.com', 3),
(27, 'wali_sandi', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Sandi Pratama', 'wali.sandi@email.com', 3),
(28, 'wali_shakila', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Shakila Atmarini', 'wali.shakila@email.com', 3),
(29, 'wali_aisyah', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Siti Aisyah', 'wali.aisyah@email.com', 3),
(30, 'wali_syakira', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Syakira Adelia', 'wali.syakira@email.com', 3),
(31, 'wali_yumna', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Yumna Qanita', 'wali.yumna@email.com', 3),
(32, 'wali_zahira', '$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N', 'Wali Zahira Maureta', 'wali.zahira@email.com', 3);

-- 2. Insert Anak (29 Anak)
-- Masing-masing terhubung ke wali uniknya (id_wali 4 s/d 32) dan guru (id_guru 3)
INSERT INTO anak (id_anak, nama_anak, tgl_lahir, jenis_kelamin, id_guru, id_wali) VALUES 
(1, 'AHMAD AZZAM', '2019-01-15', 'L', 3, 4),
(2, 'AHMAD RAFIF ALFAREZI', '2019-03-22', 'L', 3, 5),
(3, 'AL SYAFA ANIRAH', '2019-06-10', 'P', 3, 6),
(4, 'ALESHA PUTRI ADIBA', '2019-08-05', 'P', 3, 7),
(5, 'APRI YANSA', '2018-12-11', 'L', 3, 8),
(6, 'ARAFAH ALMAHYRA AHMAD', '2019-11-02', 'P', 3, 9),
(7, 'ARSYILA RATIFAH', '2019-04-18', 'P', 3, 10),
(8, 'AZKIYA YUMNA', '2019-07-29', 'P', 3, 11),
(9, 'CASSANOVA NISA', '2019-02-14', 'P', 3, 12),
(10, 'CLARA SALSABILA', '2019-09-09', 'P', 3, 13),
(11, 'DAFIANSYAH', '2019-05-30', 'L', 3, 14),
(12, 'HAFIZH ARYAGUNA', '2019-03-12', 'L', 3, 15),
(13, 'M NUSA SAPUTRA', '2019-10-25', 'L', 3, 16),
(14, 'M RAFFA AZKA PUTRA', '2019-01-08', 'L', 3, 17),
(15, 'M. AMAR ATHALA', '2019-08-17', 'L', 3, 18),
(16, 'MUHAMMAD ABIL', '2019-04-04', 'L', 3, 19),
(17, 'MUHAMMAD AJI SANGKUT', '2019-12-12', 'L', 3, 20),
(18, 'MUHAMMAD AL MAHDI', '2019-07-07', 'L', 3, 21),
(19, 'MUHAMMAD REZKY PUTRA', '2018-11-20', 'L', 3, 22),
(20, 'NOUREEN MIKAYLA', '2019-02-28', 'P', 3, 23),
(21, 'RAFIF SHIDQI ATHALLAH', '2019-06-16', 'L', 3, 24),
(22, 'RAFKA ARSHA KIMIZUKY', '2019-09-30', 'L', 3, 25),
(23, 'RANIA PUTRI AIRIN', '2019-05-05', 'P', 3, 26),
(24, 'SANDI PRATAMA', '2019-01-21', 'L', 3, 27),
(25, 'SHAKILA ATMARINI', '2019-08-11', 'P', 3, 28),
(26, 'SITI AISYAH', '2019-03-03', 'P', 3, 29),
(27, 'SYAKIRA ADELIA PUTRI', '2019-10-10', 'P', 3, 30),
(28, 'YUMNA QANITA', '2019-04-24', 'P', 3, 31),
(29, 'ZAHIRA MAURETA', '2019-07-15', 'P', 3, 32);

-- 3. Insert Perkembangan Fisik (Dummy Data untuk semua anak)
INSERT INTO perkembangan_fisik (id_anak, tanggal, tinggi_badan, berat_badan, lingkar_kepala, usia_bulan, catatan) VALUES 
(1, '2024-01-20', 105.0, 18.0, 50.0, 60, 'Sehat'),
(2, '2024-01-20', 106.0, 19.0, 51.0, 58, 'Aktif'),
(3, '2024-01-20', 104.0, 17.5, 49.0, 55, 'Normal'),
(4, '2024-01-20', 103.0, 16.0, 48.0, 53, 'Mungil tapi sehat'),
(5, '2024-01-20', 110.0, 20.0, 52.0, 61, 'Tinggi'),
(6, '2024-01-20', 102.0, 16.5, 49.0, 50, 'Lincah'),
(7, '2024-01-20', 105.0, 18.0, 50.0, 57, 'Baik'),
(8, '2024-01-20', 104.5, 17.8, 49.5, 54, 'Sehat'),
(9, '2024-01-20', 103.0, 17.0, 48.5, 59, 'Normal'),
(10, '2024-01-20', 101.0, 15.5, 48.0, 52, 'Perlu tambah berat badan'),
(11, '2024-01-20', 107.0, 19.5, 51.0, 56, 'Kuat'),
(12, '2024-01-20', 106.0, 18.5, 50.5, 58, 'Aktif sekali'),
(13, '2024-01-20', 102.0, 16.0, 49.0, 51, 'Sehat'),
(14, '2024-01-20', 108.0, 19.0, 51.5, 60, 'Pertumbuhan bagus'),
(15, '2024-01-20', 103.0, 17.0, 49.0, 53, 'Normal'),
(16, '2024-01-20', 105.0, 18.0, 50.0, 57, 'Sehat'),
(17, '2024-01-20', 100.0, 15.0, 48.0, 49, 'Cukup'),
(18, '2024-01-20', 104.0, 17.5, 49.5, 54, 'Baik'),
(19, '2024-01-20', 109.0, 20.0, 52.0, 62, 'Tinggi besar'),
(20, '2024-01-20', 102.5, 16.5, 48.5, 59, 'Aktif'),
(21, '2024-01-20', 106.0, 18.5, 50.5, 55, 'Sehat'),
(22, '2024-01-20', 101.5, 16.0, 48.0, 51, 'Lincah'),
(23, '2024-01-20', 104.0, 17.0, 49.0, 56, 'Normal'),
(24, '2024-01-20', 107.5, 19.0, 51.0, 60, 'Kuat'),
(25, '2024-01-20', 103.0, 16.8, 48.5, 53, 'Manis'),
(26, '2024-01-20', 105.0, 18.0, 50.0, 58, 'Sehat'),
(27, '2024-01-20', 102.0, 16.0, 49.0, 51, 'Baik'),
(28, '2024-01-20', 104.5, 17.5, 49.5, 57, 'Aktif'),
(29, '2024-01-20', 103.5, 17.0, 49.0, 54, 'Sehat');

-- 4. Insert Perkembangan Aspek (Nilai Random)
INSERT INTO perkembangan_aspek (id_anak, tanggal, agama_moral, fisik_motorik, kognitif, bahasa, sosial_emosional, seni, catatan) VALUES 
(1, '2024-01-20', 80, 85, 80, 85, 80, 85, 'Baik'),
(2, '2024-01-20', 85, 90, 85, 80, 85, 90, 'Sangat Baik'),
(3, '2024-01-20', 75, 80, 75, 80, 75, 80, 'Cukup Baik'),
(4, '2024-01-20', 90, 85, 90, 90, 85, 80, 'Istimewa'),
(5, '2024-01-20', 80, 80, 80, 80, 80, 80, 'Stabil'),
(6, '2024-01-20', 85, 85, 85, 85, 85, 85, 'Seimbang'),
(7, '2024-01-20', 80, 75, 80, 85, 80, 75, 'Perlu bimbingan motorik'),
(8, '2024-01-20', 88, 88, 88, 88, 88, 88, 'Konsisten'),
(9, '2024-01-20', 70, 75, 75, 70, 75, 75, 'Perlu dorongan'),
(10, '2024-01-20', 82, 82, 82, 82, 82, 82, 'Baik'),
(11, '2024-01-20', 85, 90, 80, 85, 90, 80, 'Aktif'),
(12, '2024-01-20', 90, 80, 90, 80, 90, 80, 'Cerdas'),
(13, '2024-01-20', 78, 78, 78, 78, 78, 78, 'Cukup'),
(14, '2024-01-20', 85, 85, 90, 85, 80, 85, 'Bagus'),
(15, '2024-01-20', 80, 80, 80, 80, 80, 80, 'Rata-rata'),
(16, '2024-01-20', 86, 84, 86, 84, 86, 84, 'Variatif'),
(17, '2024-01-20', 75, 75, 75, 75, 75, 75, 'Cukup'),
(18, '2024-01-20', 92, 90, 92, 90, 92, 90, 'Sangat Bagus'),
(19, '2024-01-20', 80, 85, 80, 85, 80, 85, 'Baik'),
(20, '2024-01-20', 88, 80, 88, 80, 88, 80, 'Menonjol'),
(21, '2024-01-20', 76, 80, 76, 80, 76, 80, 'Sedang'),
(22, '2024-01-20', 84, 86, 84, 86, 84, 86, 'Baik'),
(23, '2024-01-20', 80, 80, 80, 80, 80, 80, 'Normal'),
(24, '2024-01-20', 89, 89, 89, 89, 89, 89, 'Tinggi'),
(25, '2024-01-20', 78, 82, 78, 82, 78, 82, 'Cukup Baik'),
(26, '2024-01-20', 85, 85, 85, 85, 85, 85, 'Stabil'),
(27, '2024-01-20', 90, 85, 90, 85, 90, 85, 'Pintar'),
(28, '2024-01-20', 80, 80, 80, 80, 80, 80, 'Baik'),
(29, '2024-01-20', 85, 88, 85, 88, 85, 88, 'Ceria');



