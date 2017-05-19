package com.jp.movieview.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jp.movieview.R;
import com.jp.movieview.ui.fragment.AllComicsFragment;
import com.jp.movieview.ui.fragment.IndexCoimcsFragment;


public class ComicsMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FragmentManager manager;
    AllComicsFragment fragment1;
    IndexCoimcsFragment fragment2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

         fragment1=new AllComicsFragment();
         fragment2=new IndexCoimcsFragment();

        manager=getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.content,fragment1)
                .add(R.id.content,fragment2)
                .show(fragment2)
                .hide(fragment1)
                .commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_yande) {
            // Handle the camera action
        } else if (id == R.id.nav_konachan) {
            manager=getSupportFragmentManager();
            manager.beginTransaction().show(fragment1)
                    .hide(fragment2)
                    .commit();


        } else if (id == R.id.nav_comic) {
            manager=getSupportFragmentManager();
            manager.beginTransaction().show(fragment2)
                    .hide(fragment1)
                    .commit();

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
