package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project.Fragment.PasswordFragment;
import com.example.project.Fragment.DeviceFragment;
import com.example.project.Fragment.HomeFragment;
import com.example.project.Fragment.LogoutFragment;
import com.example.project.Fragment.MyProfileFragment;
import com.example.project.Fragment.SettingFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SETTING = 1;
    private static final int FRAGMENT_LOGOUT = 2;
    private static final int FRAGMENT_MY_PROFILE = 3;
    private static final int FRAGMENT_CHANGE_PASSWORD = 4;
    // create for current fragment
    private int currentFragment = FRAGMENT_HOME;

    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        replaceFragment(new HomeFragment());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            openHomeFragment();
        } else if (id == R.id.nav_setting) {
            openSettingFragment();
        } else if (id == R.id.nav_logout) {
            openLogoutFragment();
        } else if (id == R.id.nav_my_profile) {
            openMyProfileFragment();
        } else if (id == R.id.nav_change_password) {
            openChangePasswordFragment();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void openHomeFragment() {
        if (currentFragment != FRAGMENT_HOME) {
            replaceFragment(new HomeFragment());
            currentFragment = FRAGMENT_HOME;
        }
    }
    private void openSettingFragment() {
        if (currentFragment != FRAGMENT_SETTING) {
            replaceFragment(new SettingFragment());
            currentFragment = FRAGMENT_SETTING;
        }
    }
    private void openLogoutFragment() {
        if (currentFragment != FRAGMENT_LOGOUT) {
            replaceFragment(new LogoutFragment());
            currentFragment = FRAGMENT_LOGOUT;
        }
    }

    private void openMyProfileFragment() {
        if (currentFragment != FRAGMENT_MY_PROFILE) {
            replaceFragment(new MyProfileFragment());
            currentFragment = FRAGMENT_MY_PROFILE;
        }
    }

    private void openChangePasswordFragment() {
        if (currentFragment != FRAGMENT_CHANGE_PASSWORD) {
            replaceFragment(new PasswordFragment());
            currentFragment = FRAGMENT_CHANGE_PASSWORD;
        }
    }


    public void gotoDeviceFragment(String nameRoom , int index){

        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        DeviceFragment deviceFragment  = new DeviceFragment();

        Bundle bundle = new Bundle();
        bundle.putString("NameRoom" , nameRoom);
        bundle.putInt("index" , index);

        deviceFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frame, deviceFragment);
        fragmentTransaction.addToBackStack(DeviceFragment.TAG);
        fragmentTransaction.commit();

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame , fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void saveRoomList(List<Room> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
        Toast.makeText(MainActivity.this, "Save complete", Toast.LENGTH_SHORT).show();
    }

    public List<Room> getRoomList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        if (json != null) {
            Type type = new TypeToken<List<Room>>() {}.getType();
            List<Room> roomList = gson.fromJson(json, type);
            return roomList;
        } else {
            return new ArrayList<>();
        }
    }
}