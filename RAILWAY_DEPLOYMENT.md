# Railway Deployment Guide

Complete guide for deploying the Monitoring Anak Backend to Railway.

## Prerequisites

- Railway account (sign up at [railway.app](https://railway.app))
- Git repository (GitHub, GitLab, or Bitbucket)
- MySQL database prepared

---

## Step 1: Prepare Your Repository

1. Ensure all code changes are committed:
```bash
git add .
git commit -m "Prepare for Railway deployment"
git push origin main
```

2. Verify these files exist in your repository:
   - `Procfile` - Tells Railway how to start the app
   - `system.properties` - Specifies Java 17
   - `pom.xml` - Maven configuration
   - `src/main/resources/application-prod.properties` - Production settings

---

## Step 2: Create a New Railway Project

1. Log in to [Railway Dashboard](https://railway.app/dashboard)
2. Click **"New Project"**
3. Select **"Deploy from GitHub repo"**
4. Choose your `monitoring-anak-backend` repository
5. Railway will automatically detect it as a Maven/Java project

---

## Step 3: Add MySQL Database

1. In your Railway project, click **"+ New"**
2. Select **"Database"** → **"MySQL"**
3. Railway will automatically provision a MySQL database
4. The following environment variables will be auto-created:
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`

---

## Step 4: Configure Environment Variables

Go to your application service → **Variables** tab and add:

### Required Variables

```bash
# Database Configuration
DATABASE_URL=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}?useSSL=true&serverTimezone=UTC

# JWT Secret (generate a strong random string)
JWT_SECRET=your-super-secure-random-string-minimum-256-bits-change-this

# CORS Origins (your frontend URL)
CORS_ORIGINS=https://your-frontend-domain.com,https://www.your-frontend-domain.com

# Spring Profile
SPRING_PROFILES_ACTIVE=prod
```

### Optional Variables

```bash
# Logging
LOG_LEVEL=INFO
APP_LOG_LEVEL=INFO

# JWT Expiration (24 hours in milliseconds)
JWT_EXPIRATION=86400000

# File Upload Directory
UPLOAD_DIR=/app/uploads/laporan
```

---

## Step 5: Import Database Schema

### Option A: Using Railway MySQL Client

1. In Railway dashboard, click on your **MySQL service**
2. Click **"Connect"** → **"MySQL Client"**
3. This opens a web-based SQL editor
4. Copy and paste the contents of `schema.sql`
5. Execute the SQL script

### Option B: Using MySQL CLI

1. Get connection details from Railway MySQL service → **Connect** tab
2. Connect using MySQL client:
```bash
mysql -h <MYSQLHOST> -P <MYSQLPORT> -u <MYSQLUSER> -p<MYSQLPASSWORD> <MYSQLDATABASE>
```

3. Import the schema:
```bash
SOURCE /path/to/schema.sql;
```

---

## Step 6: Deploy the Application

1. Railway automatically builds and deploys when you push to git
2. Click on your application service
3. Go to **"Deployments"** tab to monitor build progress
4. Wait for the build to complete (usually 3-5 minutes)

### Build Process:
- Railway runs `mvn clean install`
- Creates executable JAR file
- Starts application using the `Procfile` command

---

## Step 7: Verify Deployment

### Check Application Logs

1. In Railway dashboard, click on your application service
2. Go to **"Logs"** tab
3. Look for successful startup messages:
```
Started MonitoringAnakApplication in X.XXX seconds
```

### Test the API

1. Get your application URL from Railway (e.g., `https://your-app.up.railway.app`)
2. Test the health/auth endpoint:

```bash
# Test login
curl -X POST https://your-app.up.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

3. Should return a JWT token if successful

---

## Step 8: Set Up Custom Domain (Optional)

1. In Railway project settings → **Settings** tab
2. Click **"Generate Domain"** for a free Railway subdomain
3. Or add your **Custom Domain**:
   - Enter your domain name
   - Add CNAME record to your DNS: `CNAME your-domain.com → your-app.up.railway.app`
   - Wait for DNS propagation (can take up to 48 hours)

---

## Troubleshooting

### Build Fails

**Issue**: Maven build fails
**Solution**:
- Check `pom.xml` for errors
- Ensure Java 17 is specified in `system.properties`
- Check Railway build logs for specific error messages

### Database Connection Error

**Issue**: Cannot connect to MySQL
**Solution**:
- Verify `DATABASE_URL` environment variable is correct
- Ensure MySQL service is running in Railway
- Check database credentials in environment variables

### Application Won't Start

**Issue**: Application crashes on startup
**Solution**:
- Check application logs in Railway dashboard
- Verify all required environment variables are set
- Ensure `SPRING_PROFILES_ACTIVE=prod` is set
- Check that JWT_SECRET is defined

### CORS Errors

**Issue**: Frontend cannot connect due to CORS
**Solution**:
- Add your frontend domain to `CORS_ORIGINS` environment variable
- Format: `https://frontend.com,https://www.frontend.com`
- Redeploy after changing environment variables

---

## Environment Variables Reference

| Variable | Required | Description | Example |
|----------|----------|-------------|---------|
| `DATABASE_URL` | Yes | JDBC connection string | `jdbc:mysql://host:port/db` |
| `MYSQLUSER` | Yes | Database username | Auto-provided by Railway |
| `MYSQLPASSWORD` | Yes | Database password | Auto-provided by Railway |
| `JWT_SECRET` | Yes | Secret key for JWT | Random 256-bit string |
| `CORS_ORIGINS` | Yes | Allowed frontend URLs | `https://app.com` |
| `SPRING_PROFILES_ACTIVE` | Yes | Spring profile | `prod` |
| `PORT` | No | Application port | Auto-provided by Railway |
| `LOG_LEVEL` | No | Logging level | `INFO` (default) |
| `JWT_EXPIRATION` | No | Token expiry (ms) | `86400000` (24h) |

---

## Post-Deployment Checklist

- [ ] Application deployed successfully
- [ ] Database schema imported
- [ ] All environment variables configured
- [ ] Test admin login works
- [ ] Test guru login works
- [ ] Test wali login works
- [ ] Verify role-based access control
- [ ] Test API endpoints with JWT tokens
- [ ] Configure custom domain (if needed)
- [ ] Set up monitoring/alerts (Railway provides this)

---

## Maintenance

### View Logs
```
Railway Dashboard → Your Service → Logs
```

### Redeploy
```bash
# Push to git triggers automatic redeployment
git push origin main
```

### Update Environment Variables
```
Railway Dashboard → Your Service → Variables → Add/Edit
Note: Requires manual redeploy after changes
```

### Database Backup
Railway provides automatic daily backups for MySQL.
Access via: **MySQL Service → Backups**

---

## Security Best Practices

1. **JWT Secret**: Use a strong, random 256-bit secret
2. **Environment Variables**: Never commit secrets to git
3. **HTTPS**: Railway provides SSL/TLS automatically
4. **Database**: Use strong password (Railway auto-generates)
5. **CORS**: Only allow trusted frontend domains
6. **Monitoring**: Enable Railway monitoring and alerts

---

## Cost Estimate

Railway pricing (as of 2026):
- **Hobby Plan**: $5/month + usage
- **MySQL**: ~$0.01/GB/month
- **Compute**: ~$0.000463/minute

Estimated monthly cost: **$10-20** for a small production app

---

## Support

- Railway Documentation: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
- Project Issues: Use GitHub issues for bug reports
