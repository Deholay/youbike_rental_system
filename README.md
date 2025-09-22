# YouBike Android App

一個示範性 YouBike Android 專案，包含地圖站點顯示、租借流程、使用者登入/加值等功能。

## 需求
- Android Studio (Giraffe 或以上)
- JDK 17

## 建置
```bash
./gradlew assembleDebug
# 產出: app/build/outputs/apk/debug/app-debug.apk
```

## 發佈 Release APK/AAB
1. 準備簽章（請勿提交到 Git）：`.jks`/`.keystore`、密碼。
2. 產生 Release 版本：
```bash
# APK
./gradlew assembleRelease
# AAB（上架 Google Play 推薦）
./gradlew bundleRelease
```
- 產出位置：
  - APK: `app/build/outputs/apk/release/app-release.apk`
  - AAB: `app/build/outputs/bundle/release/app-release.aab`
3. 到 GitHub 建立 Release，附上 APK/AAB 與更新說明。

## 目錄結構
- `app/src/`：原始碼與資源
- `gradlew`, `gradlew.bat`, `gradle/wrapper/`：Gradle Wrapper（建議提交）
- `app/src/main/assets/`：站點/車樁 JSON 資料

## 不應提交（已在 .gitignore）
- `**/build/`, `.gradle/`, `.idea/`, `*.iml`, `.DS_Store`
- `local.properties`
- 簽章與敏感資訊：`*.jks`, `*.keystore`, `keystore.properties`

## 授權
請自行補上 `LICENSE`（例如 MIT 或 Apache-2.0）。
