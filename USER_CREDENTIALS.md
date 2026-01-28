# User Credentials Documentation

This document contains all user accounts and their passwords for the Monitoring Anak Backend system.

> [!IMPORTANT]
> **Security Notice**: These are test accounts with dummy passwords. In a real production environment, users should change their passwords after first login.

## Password Scheme

Different passwords are used for each role type to enhance security:

| Role | Password | BCrypt Hash |
|------|----------|-------------|
| Admin | `admin123` | `$2a$10$5F8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7` |
| Guru | `guru123` | `$2a$10$N.5F8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L` |
| Wali Murid | `wali123` | `$2a$10$8K7R9yLxJ3X.8zP.qNZeF/LQZ4.J5N8X9vHJK2Z3Y4wRJK5L6M7N` |

---

## Admin Accounts

Administrators have full system access and can manage all data.

| Username | Password | Name | Email |
|----------|----------|------|-------|
| `admin` | `admin123` | Administrator | admin@sekolah.com |
| `admin_sekolah` | `admin123` | Admin Sekolah | admin.sekolah@sekolah.com |

**Admin Capabilities:**
- View all children and their data
- Create, update, and delete student records
- Manage reports and development data
- Full access to all API endpoints

---

## Guru (Teacher) Account

Teachers can view all students and manage their development data.

| Username | Password | Name | Email |
|----------|----------|------|-------|
| `guru_budi` | `guru123` | Budi Santoso | budi@sekolah.com |

**Guru Capabilities:**
- View all children (29 students)
- Input and update development records (physical & aspect)
- Create and manage reports
- Same permissions as admin except system administration

---

## Wali Murid (Guardian) Accounts

Guardians can only view their own children's data. Each guardian has exactly one child assigned.

| Username | Password | Name | Email | Child Name |
|----------|----------|------|-------|------------|
| `wali_azzam` | `wali123` | Wali Ahmad Azzam | wali.azzam@email.com | AHMAD AZZAM |
| `wali_rafif` | `wali123` | Wali Ahmad Rafif | wali.rafif@email.com | AHMAD RAFIF ALFAREZI |
| `wali_ani` | `wali123` | Wali Al Syafa Anirah | wali.ani@email.com | AL SYAFA ANIRAH |
| `wali_alesha` | `wali123` | Wali Alesha Putri | wali.alesha@email.com | ALESHA PUTRI ADIBA |
| `wali_apri` | `wali123` | Wali Apri Yansa | wali.apri@email.com | APRI YANSA |
| `wali_arafah` | `wali123` | Wali Arafah Almahyra | wali.arafah@email.com | ARAFAH ALMAHYRA AHMAD |
| `wali_arsyila` | `wali123` | Wali Arsyila Ratifah | wali.arsyila@email.com | ARSYILA RATIFAH |
| `wali_azkiya` | `wali123` | Wali Azkiya Yumna | wali.azkiya@email.com | AZKIYA YUMNA |
| `wali_cassanova` | `wali123` | Wali Cassanova Nisa | wali.cassanova@email.com | CASSANOVA NISA |
| `wali_clara` | `wali123` | Wali Clara Salsabila | wali.clara@email.com | CLARA SALSABILA |
| `wali_dafian` | `wali123` | Wali Dafiansyah | wali.dafiansyah@email.com | DAFIANSYAH |
| `wali_hafizh` | `wali123` | Wali Hafizh Aryaguna | wali.hafizh@email.com | HAFIZH ARYAGUNA |
| `wali_nusa` | `wali123` | Wali M Nusa Saputra | wali.nusa@email.com | M NUSA SAPUTRA |
| `wali_razka` | `wali123` | Wali M Raffa Azka | wali.razka@email.com | M RAFFA AZKA PUTRA |
| `wali_amar` | `wali123` | Wali M Amar Athala | wali.amar@email.com | M. AMAR ATHALA |
| `wali_abil` | `wali123` | Wali Muhammad Abil | wali.abil@email.com | MUHAMMAD ABIL |
| `wali_aji` | `wali123` | Wali Muhammad Aji | wali.aji@email.com | MUHAMMAD AJI SANGKUT |
| `wali_mahdi` | `wali123` | Wali Muhammad Al Mahdi | wali.mahdi@email.com | MUHAMMAD AL MAHDI |
| `wali_rezky` | `wali123` | Wali Muhammad Rezky | wali.rezky@email.com | MUHAMMAD REZKY PUTRA |
| `wali_noureen` | `wali123` | Wali Noureen Mikayla | wali.noureen@email.com | NOUREEN MIKAYLA |
| `wali_rafif_s` | `wali123` | Wali Rafif Shidqi | wali.rafif_s@email.com | RAFIF SHIDQI ATHALLAH |
| `wali_rafka` | `wali123` | Wali Rafka Arsha | wali.rafka@email.com | RAFKA ARSHA KIMIZUKY |
| `wali_rania` | `wali123` | Wali Rania Putri | wali.rania@email.com | RANIA PUTRI AIRIN |
| `wali_sandi` | `wali123` | Wali Sandi Pratama | wali.sandi@email.com | SANDI PRATAMA |
| `wali_shakila` | `wali123` | Wali Shakila Atmarini | wali.shakila@email.com | SHAKILA ATMARINI |
| `wali_aisyah` | `wali123` | Wali Siti Aisyah | wali.aisyah@email.com | SITI AISYAH |
| `wali_syakira` | `wali123` | Wali Syakira Adelia | wali.syakira@email.com | SYAKIRA ADELIA PUTRI |
| `wali_yumna` | `wali123` | Wali Yumna Qanita | wali.yumna@email.com | YUMNA QANITA |
| `wali_zahira` | `wali123` | Wali Zahira Maureta | wali.zahira@email.com | ZAHIRA MAURETA |

**Wali Capabilities:**
- View only their own child's data
- View development records for their child
- View reports for their child
- Cannot create, update, or delete any records

---

## API Testing Examples

### Login as Admin
```bash
curl -X POST http://localhost:9090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

### Login as Guru
```bash
curl -X POST http://localhost:9090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "guru_budi", "password": "guru123"}'
```

### Login as Wali
```bash
curl -X POST http://localhost:9090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "wali_azzam", "password": "wali123"}'
```

### Using the JWT Token
After login, use the returned token in the Authorization header:

```bash
curl -X GET http://localhost:9090/api/anak \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

---

## Security Recommendations

1. **Change Default Passwords**: All users should change their passwords on first login
2. **Strong JWT Secret**: Use a strong, random JWT secret in production (set via environment variable)
3. **HTTPS Only**: Always use HTTPS in production
4. **Token Expiration**: JWT tokens expire after 24 hours by default
5. **Database Security**: Ensure database credentials are properly secured
