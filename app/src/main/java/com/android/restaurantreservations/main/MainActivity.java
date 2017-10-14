package com.android.restaurantreservations.main;

import android.os.Bundle;
import com.android.restaurantreservations.R;
import com.android.restaurantreservations.base.view.BaseActivity;

import com.android.restaurantreservations.main.view.CustomerFragment;
import com.android.restaurantreservations.utils.FragmentUtils;

import static com.android.restaurantreservations.application.RestaurantReservationsConstants.CUSTOMER_FRAG_TAG;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show customers list
        FragmentUtils.replaceFragment(this, new CustomerFragment(), R.id.fragment_main_container,
              false, CUSTOMER_FRAG_TAG);

    }

}
