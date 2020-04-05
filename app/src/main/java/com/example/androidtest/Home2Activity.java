package com.example.androidtest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home2Activity extends AppCompatActivity {
    DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//      ListView listView = (ListView)findViewById(R.id.listView);
//      db = new DbManager(this);
//
//        ArrayList<String> theList =  new ArrayList<>();
//        Cursor data = db.getlistContents();
//        if (data.getCount() == 0 )
//        {
//            Toast.makeText(Home2Activity.this,"The DB is empty",Toast.LENGTH_SHORT).show();
//        }else{
//            while(data.moveToNext()){
//                theList.add(data.getString(1));
//                ListAdapter listAdapter = new  ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
//                listView.setAdapter(listAdapter);
//            }
//        }
    }

}