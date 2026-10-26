# FIRELAND: BORNEO FOREST RESCUE 🌳🔥

Game Simulasi Pemadam Kebakaran Hutan Lahan Gambut Kalimantan 2D (Top-Down).

Dibuat untuk platform Android menggunakan **Kotlin, Jetpack Compose, & Android Canvas 2D**.

---

## 📱 Unduh File APK Android
APK versi rilis otomatis dibuat melalui GitHub Actions CI/CD:
- **Download APK Release**: [Lihat Halaman Rilis GitHub](https://github.com/sodongmubarak-ux/Game-Kebakaran/releases)
- **Workflow Build Status**: [Lihat Proses Build APK](https://github.com/sodongmubarak-ux/Game-Kebakaran/actions)

---

## 🎮 Fitur Prototipe v1.0.0
1. **Sudut Pandang 2D Top-Down**:
   - Area Markas Relawan: Rumah panggung tradisional Dayak, kebun sayur rakyat, tandon air, dan gudang peralatan.
   - Area Misi 01: Kebakaran di tepi hutan tropis Kalimantan.
2. **Kontrol Sentuh Layar HP**:
   - Virtual Joystick analog yang responsif untuk pergerakan 4 arah.
   - Tombol **PADAMKAN** dengan deteksi jarak interaktif dan semprotan air.
   - Tombol **ISI AIR** saat berada di dekat tandon atau aliran sungai gambut.
3. **Mekanisme Permainan**:
   - Persediaan air terbatas (100 Liter).
   - Pengurangan intensitas api bertahap saat disemprot air.
   - Kemenangan misi saat api padam (hadiah +50 koin relawan).
4. **Penyimpanan Lokal (Offline)**:
   - Menggunakan SharedPreferences di Android (dan localStorage di Web).
   - Menyimpan progres koin, misi selesai, persediaan air, dan status game.
5. **Dukungan Bilingual**:
   - Bahasa Indonesia (Default) dan English.

---

## 🛠️ Struktur Proyek Android
```
Game-Kebakaran/
├── .github/workflows/
│   └── build-apk.yml            # CI/CD otomatis untuk compile APK & GitHub Release
├── app/
│   ├── src/main/
│   │   ├── java/com/fireland/borneorescue/
│   │   │   ├── MainActivity.kt        # Entry point aplikasi
│   │   │   ├── ui/
│   │   │   │   └── MainMenu.kt        # Antarmuka Jetpack Compose
│   │   │   ├── game/
│   │   │   │   ├── GameScreen.kt      # Game loop & manajemen touch
│   │   │   │   ├── GameRenderer.kt    # Render 2D Canvas (Pohon, Rumah, Api, Pemain)
│   │   │   │   ├── PlayerController.kt# Logika pergerakan & arah
│   │   │   │   ├── FireSystem.kt      # Sistem intensitas & pemadaman api
│   │   │   │   └── WaterSystem.kt     # Sistem konsumsi & isi ulang air
│   │   │   └── data/
│   │   │       ├── LocalSaveManager.kt# Penyimpanan SharedPreferences lokal
│   │   │       └── LanguageManager.kt # Sistem terpusat terjemahan ID & EN
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── settings.gradle.kts
├── build.gradle.kts
└── gradlew
```

---

## 🚀 Cara Build Manual di Android Studio
1. Buka folder proyek ini di **Android Studio Iguana / Jellyfish**.
2. Biarkan Gradle melakukan sync dependensi.
3. Jalankan menu `Build` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`.
4. File `.apk` akan siap di: `app/build/outputs/apk/debug/app-debug.apk`.
