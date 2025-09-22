# YouBike Android App

一個示範性的 YouBike 租借/地圖顯示 Android App，包含使用者登入、維修人員登入、地圖站點顯示、站點清單、加值等功能。專案已封裝主要邏輯，提升維護性。

## 特色
- Google Map 顯示站點標記與點擊導向站點詳情
- 站點清單 RecyclerView 與顯示切換
- 使用者登入/註冊、餘額加值
- 維修人員登入（預設密碼 6666，可於程式修改）
- 程式封包：地圖、資料存取、導覽、清單控制抽離

## 環境需求
- Android Studio (Giraffe+) / Gradle 8.4（含 Wrapper）
- JDK 17

## 建置
```bash
./gradlew assembleDebug
# 產出: app/build/outputs/apk/debug/app-debug.apk
```

## 發佈（Release）
- 產生已簽 APK / AAB：
```bash
./gradlew assembleRelease   # app/build/outputs/apk/release/app-release.apk
./gradlew bundleRelease     # app/build/outputs/bundle/release/app-release.aab
```
- keystore 設定（已被 .gitignore 忽略）：在專案根目錄建立 `keystore.properties`
```
storeFile=/ABS/PATH/youbike-release.jks
storePassword=xxxxxx
keyAlias=youbike
keyPassword=xxxxxx
```
- app 簽章設定：已於 `app/build.gradle.kts` 自動讀取 `keystore.properties` 並套用到 release buildType。

## GitHub Releases（網頁）
1. 到專案頁 → Releases → Draft a new release  
2. Tag: v1.0.0（或你的版本），Title: v1.0.0  
3. 說明（例如：初版釋出）  
4. 上傳 `app-release.apk` 與/或 `app-release.aab` → Publish

---

## 程式碼架構（封裝後）

### UI 組裝與導覽
- `app/src/main/java/com/example/youbike/MainActivity.java`
  - 負責：
    - AppBar、Drawer、NavController 初始化
    - 側邊欄標頭顯示登入使用者資訊
    - 使用下列控制器與服務完成功能（地圖、資料、清單）
  - 依賴：`StationRepository`、`MapController`、`StationListController`、`NavigationHandler`

- `app/src/main/java/com/example/youbike/NavigationHandler.java`
  - 抽離側邊欄選單點擊事件
  - 導向 `ProfileActivity`、`ReportActivity`、`ValueActivity`，並於需要時攜帶 `USER_EMAIL`

### 地圖與清單
- `app/src/main/java/com/example/youbike/MapController.java`
  - 在 `GoogleMap` 上渲染站點 Marker、設定初始鏡頭位置
  - 處理 Marker 點擊：第一次點擊顯示 InfoWindow；點擊 InfoWindow 進入 `StationDetailsActivity`
  - 會將 `stationUID`、`stationName` 與 `USER_EMAIL` 透過 Intent 傳遞

- `app/src/main/java/com/example/youbike/StationListController.java`
  - 封裝 RecyclerView 綁定與顯示切換（顯示/隱藏清單）
  - `bind(stations, adapter)` 與 `toggleVisibility()`

- `app/src/main/java/com/example/youbike/StationAdapter.java` / `Station.java`
  - 站點資料模型與清單項目的 Adapter

### 資料層
- `app/src/main/java/com/example/youbike/StationRepository.java`
  - 資料來源的統一入口：
    - 重置地圖資料庫 `DatabaseHelperMap.resetDatabase()`
    - 自 `assets` 讀取 `NTUStations.json`、`Docks.json` 寫入資料庫
    - 提供 `getAllStations()` 取得站點清單（供地圖與清單使用）

- `app/src/main/java/com/example/youbike/DatabaseHelperMap.java`
  - 地圖/站點/車柱 SQLite 結構與初始化

- `app/src/main/java/com/example/youbike/DatabaseHelper.java`
  - 使用者資料（email、password、username、phone、easycard、balance）的 SQLite 邏輯
  - `checkMaintenancePassword()` 預設回傳密碼比對（目前為 "6666"）

### 使用者流程（登入/註冊/加值）
- `app/src/main/java/com/example/youbike/LoginActivity.java` / `SignupActivity.java`
  - 使用 `DatabaseHelper` 驗證或寫入使用者資料

- `app/src/main/java/com/example/youbike/ui/value/ValueFragment.java` / `ValueActivity.java`
  - 顯示目前餘額、輸入加值金額、呼叫 `DatabaseHelper.updateBalance()` 更新餘額

- `app/src/main/java/com/example/youbike/MaintenanceLoginActivity.java`
  - 維修人員登入（透過 `DatabaseHelper.checkMaintenancePassword()`）

### 畫面與資源
- 版面：`app/src/main/res/layout/**`
- 選單：`app/src/main/res/menu/**`
- 文字/樣式：`app/src/main/res/values/**`
- 導航：`app/src/main/res/navigation/mobile_navigation.xml`
- 資產（JSON）：`app/src/main/assets/**`

### 專案/建置設定
- Gradle：`build.gradle.kts`（根）、`app/build.gradle.kts`（模組）、`settings.gradle.kts`
- Gradle Wrapper：`gradlew`, `gradlew.bat`, `gradle/wrapper/**`
- 忽略檔：`.gitignore`（已忽略 build 產物、local.properties、keystore 等敏感）

---

## 開發指引
- 修改維修密碼：`DatabaseHelper.checkMaintenancePassword()`
- 調整地圖互動：`MapController`（Marker 行為、鏡頭位置）
- 調整資料來源：`StationRepository`（assets → DB 的匯入流程）
- 精簡/擴充主流程：在 `MainActivity` 組裝與注入控制器

## 授權
請自行補上 `LICENSE`（例如 MIT 或 Apache-2.0）。
