package com.ezee.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.ezee.Fragments.MainCategoryFragment;
import com.ezee.Fragments.ProductFragment;
import com.ezee.Models.ProductCategory;
import com.ezee.R;

public class MainCategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
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
        MainCategoryFragment productFragment = new MainCategoryFragment();
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
                MainCategoryFragment mainfragment=new MainCategoryFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mainfragment)
                        .commit();
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

                break;
            case R.id.navigation_item_5:
                mTitle = getString(R.string.title_payment);
                position = 3;
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}