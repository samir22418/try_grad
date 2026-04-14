# 🛡️ SecureGuard — Android Security Scanner

> A graduation project Android application that scans all installed apps on a device, analyzes their permissions, calculates risk scores, and uploads the raw scan data to a central server for research and analysis.

---

## 📱 Overview

**SecureGuard** helps users understand the security posture of their Android device by:

- Scanning every installed application and extracting its permissions
- Assigning a **risk score (0–100)** based on dangerous permission usage
- Classifying apps into **Low / Medium / High / Critical** risk categories
- Persisting all scan results locally using **Room Database**
- Uploading the complete raw dataset to a **central server** via REST API

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔍 **Full Device Scan** | Scans all installed apps (user & system) using `PackageManager` |
| ⚠️ **Permission Analysis** | Identifies 20+ dangerous permissions (camera, SMS, location, contacts…) |
| 📊 **Risk Scoring** | Calculates a weighted 0–100 risk score per app |
| 🗄️ **Local Storage** | Persists results in a Room database for offline access |
| ☁️ **Server Sync** | POSTs raw scan data to a central REST API server |
| 🔎 **Search & Filter** | Real-time search + filter by All / User / System / High Risk |
| 📈 **Dashboard** | Animated security score ring + risk distribution bar |
| 🌙 **Dark Theme** | Cybersecurity-themed dark UI with animated components |

---

## 🏗️ Architecture

The project follows **Clean Architecture** with MVVM pattern:

```
app/
└── src/main/java/com/example/try_grad/
    ├── data/
    │   ├── local/
    │   │   ├── dao/            # Room DAO (AppDao)
    │   │   ├── database/       # AppDatabase (Room)
    │   │   └── entities/       # AppEntity (DB table)
    │   ├── models/             # Domain models (AppInfo, RiskLevel)
    │   ├── remote/
    │   │   ├── api/            # Retrofit interface (ApiService)
    │   │   ├── models/         # API DTOs (DeviceReportRequest, SyncResponse…)
    │   │   └── RetrofitClient  # OkHttp + Retrofit singleton
    │   └── repository/         # AppRepository (bridge layer)
    ├── domain/
    │   └── scanner/            # AppScanner (core scanning engine)
    ├── ui/
    │   ├── screens/            # MainScreen (Jetpack Compose UI)
    │   ├── viewmodels/         # MainViewModel + UiState
    │   └── theme/              # Color, Theme, Typography
    ├── MainActivity.kt
    └── SecurityScannerApp.kt
```

---

## 🔐 Risk Scoring

Each app is scored from **0 to 100**:

| Risk Level | Score | Color |
|---|---|---|
| 🟢 Low | 0 – 29 | Green |
| 🟡 Medium | 30 – 59 | Yellow |
| 🟠 High | 60 – 79 | Orange |
| 🔴 Critical | 80 – 100 | Red |

**Score factors:**
- +5 pts per dangerous permission (capped at 50)
- +12 pts for READ/SEND_SMS
- +10 pts for CAMERA, RECORD_AUDIO, READ_PHONE_STATE
- +8 pts for ACCESS_FINE_LOCATION, READ_CONTACTS
- +5 pts for INTERNET
- System apps receive a **70% score reduction**

---

## ☁️ Central Server API

The app can upload the full scan report to a REST backend.

### Endpoint

```
POST /api/v1/reports
```

### Request Body

```json
{
  "device_id": "abc123",
  "device_model": "Samsung Galaxy S21",
  "android_version": "13",
  "scan_timestamp": 1712870400000,
  "apps": [
    {
      "package_name": "com.example.app",
      "app_name": "Example App",
      "version_name": "1.0.0",
      "version_code": 1,
      "install_date": 1700000000000,
      "update_date": 1710000000000,
      "is_system_app": false,
      "risk_score": 72,
      "risk_level": "HIGH",
      "dangerous_permissions_count": 5,
      "normal_permissions_count": 3,
      "all_permissions": ["android.permission.CAMERA", "..."],
      "scan_date": 1712870400000
    }
  ]
}
```

### Response

```json
{
  "success": true,
  "message": "Report received",
  "report_id": "rpt_xyz789",
  "total_apps": 120,
  "high_risk_count": 8,
  "server_timestamp": 1712870401000
}
```

### Configuration

To point the app at your server, edit `RetrofitClient.kt`:

```kotlin
private const val BASE_URL = "https://your-server.com/"
```

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository Pattern |
| Local DB | Room (with KSP annotation processing) |
| Networking | Retrofit 2 + OkHttp 4 + Gson |
| Async | Kotlin Coroutines + Flow |
| Background | WorkManager *(planned)* |
| Permissions | Accompanist Permissions |
| Navigation | Navigation Compose *(planned)* |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Android device / emulator with API 24+
- (Optional) A backend server matching the API contract above

### Build & Run

```bash
# Clone the repository
git clone https://github.com/your-username/try_grad.git
cd try_grad

# Open in Android Studio and run on a device/emulator
```

### Required Permissions

The app will request the following at runtime:

| Permission | Purpose |
|---|---|
| `QUERY_ALL_PACKAGES` | List all installed apps |
| `PACKAGE_USAGE_STATS` | Real-time usage monitoring (must be granted manually in Settings) |
| `INTERNET` | Upload scan data to the central server |
| `ACCESS_NETWORK_STATE` | Check connectivity before syncing |
| `READ_MEDIA_IMAGES` | (Android 13+) Media access |

> ⚠️ **Usage Stats** cannot be requested via the normal permission dialog. The app will show a banner directing the user to *Settings → Apps → Special App Access → Usage Access*.

---

## 📁 Key Files

| File | Role |
|---|---|
| [`AppScanner.kt`](app/src/main/java/com/example/try_grad/domain/scanner/AppScanner.kt) | Scans installed packages and calculates risk scores |
| [`AppRepository.kt`](app/src/main/java/com/example/try_grad/data/repository/AppRepository.kt) | Coordinates local DB reads/writes and server sync |
| [`RetrofitClient.kt`](app/src/main/java/com/example/try_grad/data/remote/RetrofitClient.kt) | Retrofit + OkHttp singleton — **set your BASE_URL here** |
| [`ApiModels.kt`](app/src/main/java/com/example/try_grad/data/remote/models/ApiModels.kt) | Request/response DTOs for the central server |
| [`MainViewModel.kt`](app/src/main/java/com/example/try_grad/ui/viewmodels/MainViewModel.kt) | UI state, scan trigger, sync trigger, filtering logic |
| [`MainScreen.kt`](app/src/main/java/com/example/try_grad/ui/screens/MainScreen.kt) | Full Compose dashboard UI |

---

## 🗺️ Roadmap

- [x] Device-wide app scanning
- [x] Dangerous permission detection
- [x] Risk scoring engine
- [x] Local Room database persistence
- [x] REST API sync to central server
- [x] Dashboard UI with security score ring
- [ ] App detail screen (per-app permission breakdown)
- [ ] WorkManager background scans (scheduled)
- [ ] Real app icon display
- [ ] Backend server implementation
- [ ] Multi-device comparison on server dashboard

---

## 👨‍🎓 Author

Graduation project — Computer Science / Cybersecurity  
Built with Kotlin + Jetpack Compose for Android
