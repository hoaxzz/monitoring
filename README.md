# README - Monitoring Anak Backend

Backend API untuk Sistem Monitoring Perkembangan Anak (TK/PAUD)

## 📋 Teknologi yang Digunakan

- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Authentication**: JWT (JSON Web Token)
- **Password Encoding**: BCrypt
- **ORM**: Spring Data JPA / Hibernate

## 🗂️ Struktur Project

```
monitoring-anak-backend/
├── src/main/java/com/monitoringanak/
│   ├── controller/          # REST API Endpoints
│   ├── service/             # Business Logic
│   ├── repository/          # Database Access (JPA)
│   ├── model/               # Entity Classes
│   ├── dto/                 # Data Transfer Objects
│   ├── security/            # JWT & Security Config
│   └── MonitoringAnakApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml                  # Maven Dependencies
└── schema.sql               # Database Structure
```

## 🛠️ Setup & Installation

### 1. Prerequisites

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. Database Setup

```bash
# Buka MySQL client
mysql -u root -p

# Buat database
CREATE DATABASE monitoring_anak;

# Gunakan database
USE monitoring_anak;

# Import schema (jalankan file schema.sql)
SOURCE /path/to/schema.sql;
```

### 3. Application Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/monitoring_anak
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your-secret-key-here-change-in-production
```

### 4. Build & Run

```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

Server akan berjalan di: `http://localhost:8080/api`

## 📡 API Endpoints

### Authentication

```
POST   /api/auth/login      - Login user
POST   /api/auth/register   - Register user (admin only)
```

### Anak (Data Anak)

```
GET    /api/anak                  - Get semua anak
GET    /api/anak/{id}             - Get anak by ID
GET    /api/anak/guru/{idGuru}    - Get anak by guru
GET    /api/anak/wali/{idWali}    - Get anak by wali
POST   /api/anak                  - Create anak
PUT    /api/anak/{id}             - Update anak
DELETE /api/anak/{id}             - Delete anak
```

### Perkembangan Fisik

```
GET    /api/perkembangan/fisik/{idAnak}              - Get data fisik
GET    /api/perkembangan/fisik/{idAnak}/range        - Get by date range
GET    /api/perkembangan/fisik/detail/{idFisik}      - Get detail fisik
POST   /api/perkembangan/fisik                       - Input fisik
PUT    /api/perkembangan/fisik/{idFisik}             - Update fisik
DELETE /api/perkembangan/fisik/{idFisik}             - Delete fisik
```

### Perkembangan Aspek (6 Aspek)

```
GET    /api/perkembangan/aspek/{idAnak}              - Get data aspek
GET    /api/perkembangan/aspek/{idAnak}/range        - Get by date range
GET    /api/perkembangan/aspek/{idAnak}/average      - Get average nilai
GET    /api/perkembangan/aspek/detail/{idAspek}      - Get detail aspek
POST   /api/perkembangan/aspek                       - Input aspek
PUT    /api/perkembangan/aspek/{idAspek}             - Update aspek
DELETE /api/perkembangan/aspek/{idAspek}             - Delete aspek
```

### Laporan

```
GET    /api/laporan                      - Get semua laporan
GET    /api/laporan/{id}                 - Get laporan by ID
GET    /api/laporan/anak/{idAnak}        - Get laporan by anak
GET    /api/laporan/guru/{idGuru}        - Get laporan by guru
POST   /api/laporan                      - Create laporan
PUT    /api/laporan/{id}                 - Update laporan
DELETE /api/laporan/{id}                 - Delete laporan
```

## 📝 Contoh Request/Response

### Login

**Request:**

```json
POST /api/auth/login
{
  "username": "guru1",
  "password": "password123"
}
```

**Response:**

```json
{
  "success": true,
  "message": "Login successful",
  "code": 200,
  "data": {
    "idUser": 2,
    "username": "guru1",
    "nama": "Ibu Guru",
    "role": "guru",
    "token": "eyJhbGciOiJIUzUxMiJ9..."
  }
}
```

### Input Perkembangan Fisik

**Request:**

```json
POST /api/perkembangan/fisik
{
  "anak": {
    "idAnak": 1
  },
  "tanggal": "2024-01-27",
  "tinggiBadan": 95.5,
  "beratBadan": 15.2,
  "lingkarKepala": 48.5,
  "usiaBulan": 36,
  "catatan": "Perkembangan normal"
}
```

### Input Perkembangan Aspek

**Request:**

```json
POST /api/perkembangan/aspek
{
  "anak": {
    "idAnak": 1
  },
  "tanggal": "2024-01-27",
  "agamaMoral": 85,
  "fisikMotorik": 80,
  "kognitif": 75,
  "bahasa": 78,
  "sosialEmosional": 82,
  "seni": 80,
  "catatan": "Berkembang sangat baik"
}
```

## 🔑 Database Schema

### Tabel Utama

- **roles** - Jenis peran (admin, guru, wali_murid)
- **users** - Data user/akun login
- **anak** - Data identitas anak
- **perkembangan_fisik** - Data TB, BB, LK, usia
- **perkembangan_aspek** - Nilai 6 aspek perkembangan
- **laporan** - Laporan perkembangan & PDF
- **korelasi** - Hasil Pearson correlation (opsional)

## 🔒 Authentication

Menggunakan **JWT (JSON Web Token)**:

1. User login dengan username & password
2. Server generate JWT token
3. Client menyimpan token (localStorage/SharedPreferences)
4. Client kirim token di header: `Authorization: Bearer <token>`

## 📊 Response Format

Semua response menggunakan format yang sama:

```json
{
  "success": true/false,
  "message": "Deskripsi...",
  "code": 200,
  "data": {}
}
```

## 🧪 Testing dengan Postman

1. Buat folder "Monitoring Anak" di Postman
2. Set base URL: `http://localhost:8080/api`
3. Test endpoints:
   - POST /auth/login
   - GET /anak
   - POST /perkembangan/fisik
   - GET /laporan/{id}

## 📱 Integrasi dengan Android

Android client bisa pakai library seperti:

- **Retrofit** - HTTP client
- **OkHttp** - Interceptor untuk JWT token
- **Gson** - JSON parsing

Contoh Retrofit interface:

```java
public interface ApiService {
    @POST("auth/login")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @GET("anak")
    Call<ApiResponse<List<Anak>>> getAnak();
}
```

## 🔄 Relasi Database (ERD)

```
roles 1 ---- n users

users (guru) 1 ---- n anak
users (wali) 1 ---- n anak

anak 1 ---- n perkembangan_fisik
anak 1 ---- n perkembangan_aspek
anak 1 ---- n laporan

users (guru) 1 ---- n laporan
```

## 📚 Dokumentasi & Referensi

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JWT.io](https://jwt.io)
- [MySQL Docs](https://dev.mysql.com/doc/)

## 🤝 Kontribusi

Untuk development lebih lanjut:

1. Buat feature branch
2. Commit changes
3. Push dan buat Pull Request

## 📝 License

MIT License - Sistem Monitoring Perkembangan Anak

---

**Last Updated**: January 27, 2025
