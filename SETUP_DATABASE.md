# Setup Database MySQL - Monitoring Anak Backend

## ⚠️ Masalah yang Terjadi

Backend gagal start karena **MySQL database belum terinstall atau tidak berjalan**.

Error yang muncul:
```
Communications link failure
Connection refused: no further information
```

## 📋 Solusi - Install MySQL

Anda perlu menginstall MySQL terlebih dahulu. Ada beberapa pilihan:

### Opsi 1: Install MySQL Server (Recommended)

1. **Download MySQL Installer**
   - Kunjungi: https://dev.mysql.com/downloads/installer/
   - Download: `mysql-installer-community-8.0.xx.msi`

2. **Install MySQL**
   - Jalankan installer
   - Pilih "Developer Default" atau "Server only"
   - Set root password (bisa kosong atau sesuai keinginan)
   - Port default: 3306
   - Finish installation

3. **Buat Database**
   ```sql
   -- Buka MySQL Command Line Client atau MySQL Workbench
   CREATE DATABASE monitoring_anak;
   
   -- Import schema
   USE monitoring_anak;
   SOURCE C:/Users/Tsa19/Desktop/project/schema.sql;
   ```

### Opsi 2: Install XAMPP (Lebih Mudah)

1. **Download XAMPP**
   - Kunjungi: https://www.apachefriends.org/download.html
   - Download versi terbaru untuk Windows

2. **Install XAMPP**
   - Install ke `C:\xampp`
   - Pilih komponen: Apache, MySQL, phpMyAdmin

3. **Start MySQL**
   - Buka XAMPP Control Panel
   - Klik "Start" pada MySQL
   - MySQL akan berjalan di port 3306

4. **Buat Database**
   - Buka browser: http://localhost/phpmyadmin
   - Klik "New" untuk buat database baru
   - Nama database: `monitoring_anak`
   - Klik "Import" tab
   - Upload file: `C:\Users\Tsa19\Desktop\project\schema.sql`
   - Klik "Go"

### Opsi 3: Install MySQL via Chocolatey

Jika Chocolatey sudah terinstall:

```powershell
# Install MySQL
choco install mysql -y

# Start MySQL service
net start MySQL

# Login dan buat database
mysql -u root -p
CREATE DATABASE monitoring_anak;
USE monitoring_anak;
SOURCE C:/Users/Tsa19/Desktop/project/schema.sql;
```

## 🔧 Konfigurasi Backend

Setelah MySQL terinstall, pastikan konfigurasi di `application.properties` sesuai:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/monitoring_anak
spring.datasource.username=root
spring.datasource.password=
```

**Catatan:**
- Jika Anda set password untuk MySQL, ubah `spring.datasource.password=` menjadi password Anda
- Jika MySQL berjalan di port lain, ubah `3306` ke port yang sesuai

## ✅ Cara Cek MySQL Sudah Berjalan

### Windows Services
```powershell
Get-Service -Name "*mysql*"
```

Atau buka Task Manager → Services → cari "MySQL"

### Test Connection
```powershell
mysql -u root -p
```

Jika berhasil login, berarti MySQL sudah berjalan!

## 🚀 Jalankan Backend Setelah MySQL Ready

Setelah MySQL terinstall dan database dibuat:

```batch
cd C:\Users\Tsa19\Desktop\project\monitoring-anak-backend
.\run_backend.bat
```

Backend akan start di: **http://localhost:9090/api**

## 📝 Checklist Setup

- [ ] MySQL Server terinstall
- [ ] MySQL Service berjalan (port 3306)
- [ ] Database `monitoring_anak` sudah dibuat
- [ ] Schema sudah di-import dari `schema.sql`
- [ ] Konfigurasi `application.properties` sudah benar
- [ ] Backend berhasil start tanpa error

## 🆘 Troubleshooting

### Error: Access denied for user 'root'
**Solusi:** Ubah password di `application.properties` sesuai password MySQL Anda

### Error: Unknown database 'monitoring_anak'
**Solusi:** Database belum dibuat. Jalankan:
```sql
CREATE DATABASE monitoring_anak;
```

### Error: Port 3306 already in use
**Solusi:** Ada aplikasi lain yang pakai port 3306. Stop aplikasi tersebut atau ubah port MySQL.

## 💡 Rekomendasi

Untuk development, saya rekomendasikan **XAMPP** karena:
- ✅ Mudah diinstall
- ✅ Ada phpMyAdmin untuk manage database via GUI
- ✅ Bisa start/stop MySQL dengan mudah
- ✅ Tidak perlu konfigurasi rumit
