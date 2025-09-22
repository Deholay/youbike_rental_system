package com.example.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.youbike.databinding.ActivityMainBinding;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private String userEmail;
    private String userName;

    private StationRepository stationRepository;
    private MapController mapController;
    private StationListController stationListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        TextView navHeaderTitle = headerView.findViewById(R.id.textView);
        TextView navHeaderSubtitle = headerView.findViewById(R.id.nav_header_subtitle);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");
        userName = intent.getStringExtra("USER_NAME");
        navHeaderTitle.setText(userName);
        navHeaderSubtitle.setText(userEmail);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_value, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // 使用 NavigationHandler 抽離側邊欄點擊
        navigationView.setNavigationItemSelectedListener(new NavigationHandler(this, userEmail, navController));

        // Repository 初始化與資料導入
        stationRepository = new StationRepository(this);
        stationRepository.resetDatabase();
        stationRepository.importFromAssets();

        // 讀取站點資料
        List<Station> stations = stationRepository.getAllStations();

        // 初始化地圖
        mapController = new MapController(this, userEmail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_ID);
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                mapController.renderStations(googleMap, stations);
            });
        }

        // 初始化列表控制器
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.recycler);
        stationListController = new StationListController(recyclerView);
        stationListController.bind(stations, new StationAdapter(this, stations));

        // 列表切換
        Button toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(v -> stationListController.toggleVisibility());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
