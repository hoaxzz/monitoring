# API Documentation - Monitoring Anak Backend

Dokumentasi ini berisi daftar endpoint API yang dapat digunakan oleh Frontend.

**Base URL:** `http://localhost:9090/api`

## Format Respons Umum
Semua API (kecuali download) mengembalikan format JSON yang konsisten:
```json
{
  "success": true,
  "message": "Pesan informasi",
  "data": { ... },
  "code": 200
}
```

---

## 1. Authentication (`/auth`)

### Login
- **URL:** `/auth/login`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "username": "admin",
    "password": "password"
  }
  ```
- **Respons Sukses:**
  ```json
  {
    "token": "JWT_TOKEN_HERE",
    "role": "ROLE_ADMIN/ROLE_GURU/ROLE_WALI",
    "idUser": 1,
    "username": "admin",
    "nama": "Admin Name"
  }
  ```

### Register
- **URL:** `/auth/register`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "username": "user123",
    "password": "password123",
    "nama": "Nama Lengkap",
    "email": "email@example.com",
    "idRole": 2
  }
  ```

---

## 2. Anak (`/anak`)

### Get Semua Data Anak
- **URL:** `/anak`
- **Method:** `GET`

### Get Data Anak by ID
- **URL:** `/anak/{id}`
- **Method:** `GET`

### Tambah Data Anak
- **URL:** `/anak`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "namaAnak": "Budi",
    "tglLahir": "2020-01-01",
    "jenisKelamin": "L",
    "guru": { "idUser": 2 },
    "wali": { "idUser": 3 }
  }
  ```

---

## 3. Laporan (`/laporan`)

### Get Laporan by Anak
- **URL:** `/laporan/anak/{idAnak}`
- **Method:** `GET`

### Download File Laporan
- **URL:** `/laporan/{id}/download`
- **Method:** `GET`
- **Catatan:** Endpoint ini mengembalikan file PDF secara langsung.

### Create Laporan
- **URL:** `/laporan`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "anak": { "idAnak": 1 },
    "dibuatOleh": { "idUser": 2 },
    "periode": "Januari 2024",
    "filePdf": "nama_file.pdf"
  }
  ```

---

## 4. Perkembangan Fisik (`/perkembangan/fisik`)

### Get By Anak
- **URL:** `/perkembangan/fisik/anak/{idAnak}`
- **Method:** `GET`

### Input Perkembangan Fisik
- **URL:** `/perkembangan/fisik`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "anak": { "idAnak": 1 },
    "tanggal": "2024-01-27",
    "tinggiBadan": 110,
    "beratBadan": 18,
    "lingkarKepala": 50,
    "usiaBulan": 48
  }
  ```

---

## 5. Perkembangan Aspek (`/perkembangan/aspek`)

### Get Average Score
- **URL:** `/perkembangan/aspek/average/{idAnak}`
- **Method:** `GET`

### Input Perkembangan Aspek
- **URL:** `/perkembangan/aspek`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "anak": { "idAnak": 1 },
    "tanggal": "2024-01-27",
    "agamaMoral": 4,
    "fisikMotorik": 3,
    "kognitif": 4,
    "bahasa": 3,
    "sosialEmosional": 4,
    "seni": 3,
    "catatan": "Perkembangan sangat baik"
  }
  ```

---

## Catatan Penting
1. **CORS:** Diizinkan dari origin mana saja (`*`) untuk kemudahan pengembangan.
2. **Security:** Gunakan Header `Authorization: Bearer <token>` untuk request yang membutuhkan autentikasi (sesuai konfigurasi SecurityConfig).
3. **Data Anak:** Relasi `guru` dan `wali` bisa dikosongkan (`null`) jika belum ditentukan.
