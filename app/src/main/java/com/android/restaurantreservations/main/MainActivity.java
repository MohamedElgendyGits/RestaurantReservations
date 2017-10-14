package com.android.restaurantreservations.main;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.base.view.BaseActivity;

import com.android.restaurantreservations.main.view.CustomerFragment;
import com.android.restaurantreservations.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.restaurantreservations.application.RestaurantReservationsConstants.CUSTOMER_FRAG_TAG;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //show customers list
        FragmentUtils.replaceFragment(this, new CustomerFragment(), R.id.fragment_main_container,
              false, CUSTOMER_FRAG_TAG);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        return true;
    }

}
