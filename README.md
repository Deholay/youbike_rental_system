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

```
app/src/main/java/com/example/youbike/
├── MainActivity.java                     # 主組裝與導覽（AppBar/Drawer/NavController）
├── NavigationHandler.java                # 側邊欄點擊導覽抽離
├── db/
│   └── DatabaseHelperMap.java            # 地圖站點/車柱 SQLite 結構與初始化
├── domain/
│   └── StationRepository.java            # 匯入 assets→DB、查詢站點清單
├── feature/
│   ├── map/
│   │   └── MapController.java            # 地圖渲染、Marker 互動、跳轉詳情
│   └── list/
│       ├── Station.java                  # 站點資料模型
│       ├── StationAdapter.java           # 站點列表 Adapter
│       └── StationListController.java    # RecyclerView 綁定/顯示切換
├── data/
│   ├── LoginDataSource.java              # 使用者資料查詢（SQLite）
│   ├── LoginRepository.java              # 登入資料倉儲
│   └── model/LoggedInUser.java           # 使用者模型
├── ProfileActivity.java                  # 使用者個資顯示（從 LoginDataSource 讀）
├── LoginActivity.java                    # 使用者登入
├── SignupActivity.java                   # 使用者註冊
├── MaintenanceLoginActivity.java         # 維修登入（DatabaseHelper.checkMaintenancePassword）
├── StationDetailsActivity.java           # 站點詳情頁（租還車、扣款）
├── VehicleDispatchActivity.java          # 車輛調度（測試/範例）
└── VehicleStatusActivity.java            # 車輛狀態查詢/更新（測試/範例）
```

其他重要目錄：
```
app/src/main/
├── assets/                               # 站點/車樁 JSON（NTUStations.json、Docks.json）
└── res/
    ├── layout/                           # XML 版面（activity_*.xml, fragment_*.xml, row_*.xml）
    ├── menu/                             # 主選單與側欄選單
    ├── values/                           # colors/strings/themes
    └── navigation/mobile_navigation.xml  # NavGraph
```

### 模組關係說明
- `MainActivity`：組裝 UI 與控制器，負責導覽/注入，不承擔資料/地圖細節。
- `domain/StationRepository`：資料層入口，負責 assets→DB 匯入與站點查詢。
- `db/DatabaseHelperMap`：資料表結構與初始化（站點/車樁）。
- `feature/map/MapController`：地圖與 Marker 行為，InfoWindow 點擊跳 `StationDetailsActivity`。
- `feature/list/*`：列表 UI 封裝，`StationListController` 控制顯示切換。
- `data/*`：使用者資料查詢與登入流程封裝。

### 常見修改點
- 維修密碼：`DatabaseHelper.checkMaintenancePassword()`
- 地圖互動：`feature/map/MapController`（Marker 行為/鏡頭）
- 站點資料來源：`domain/StationRepository`（assets→DB 流程）
- 主流程調整：在 `MainActivity` 組裝/注入控制器

## 授權
請自行補上 `LICENSE`（例如 MIT 或 Apache-2.0）。
