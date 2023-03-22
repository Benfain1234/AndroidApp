package com.example.adnroidProject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG","This is from mainactivity");
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);

        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(navView,navController);

    }

    int fragmentMenuId = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        if (fragmentMenuId != 0){
            //menu.removeItem(fragmentMenuId);
        }
        fragmentMenuId = 0;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            navController.popBackStack();
        }else if(item.getItemId() == R.id.logout) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getMyContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(FirstFragment.LOCAL_SAVED_USERNAME);
            editor.commit();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            this.finish();
        }else {
                fragmentMenuId = item.getItemId();
                return NavigationUI.onNavDestinationSelected(item,navController);
            }
        return super.onOptionsItemSelected(item);
    }

}