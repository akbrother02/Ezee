package com.ezee.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezee.Fragments.MainCategoryFragment;
import com.ezee.Fragments.ProductFragment;
import com.ezee.R;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


    /**
     * Used to store the last screen title.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        TextView txtName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtName.setText(getResources().getString(R.string.title_name));
        navigationView.setNavigationItemSelectedListener(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.title_home));
        mTitle = getTitle();
        ProductFragment productFragment=new ProductFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, productFragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        int position = 0;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id) {
            case R.id.navigation_item_1:
                mTitle = getString(R.string.title_home);
                Intent HomeIntent=new Intent(ProductActivity.this,MainCategoryActivity.class);
                HomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(HomeIntent);
                break;
            case R.id.navigation_item_2:
                mTitle = getString(R.string.title_bookmarks);
                position = 1;
                break;
            case R.id.navigation_item_3:
                ProductFragment productFragment=new ProductFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, productFragment)
                        .commit();
                break;
            case R.id.navigation_item_4:
                mTitle = getString(R.string.title_payment);
                position = 3;
                break;
            case R.id.navigation_item_5:
                mTitle = getString(R.string.title_settings);
                position = 4;
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}