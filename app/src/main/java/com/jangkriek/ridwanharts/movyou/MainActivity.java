package com.jangkriek.ridwanharts.movyou;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jangkriek.ridwanharts.movyou.favorit.FavoritFragment;
import com.jangkriek.ridwanharts.movyou.main.MenuFragment;
import com.jangkriek.ridwanharts.movyou.search.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Home");

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null){
            setupViewPager(new MenuFragment(), "Home");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment = null;
        String title = "";

        if(item.getItemId()==R.id.search){
            title=getString(R.string.action_search);
            fragment = new SearchFragment();

        }
        /*if(item.getItemId()==R.id.language){
            title=getString(R.string.change_language);
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }*/
        if(item.getItemId()==R.id.action_settings){
            title=getString(R.string.action_settings);
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }

        setupViewPager(fragment, title);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        String title = "";

        if(item.getItemId()==R.id.nav_search){
            title=getString(R.string.action_search);
            fragment = new SearchFragment();

        }else if (item.getItemId()==R.id.nav_home){
            title="Home";
            fragment = new MenuFragment();

        }/*else if (item.getItemId()==R.id.nav_fav){
            title="Home";
            fragment = new FavoritFragment();

        }*/

        setupViewPager(fragment, title);
        return true;
    }

    public void setupViewPager(Fragment fragment, String title){
        if(fragment!=null){
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.content_main, fragment).addToBackStack("tag");
            fr.commit();
        }
        getSupportActionBar().setTitle(title);
        DrawerLayout dr = (DrawerLayout)findViewById(R.id.drawer_layout);
        dr.closeDrawer(GravityCompat.START);
    }

}
