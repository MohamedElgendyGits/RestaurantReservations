package com.android.restaurantreservations.main;

import android.content.Intent;
import android.os.Bundle;
import com.android.restaurantreservations.R;
import com.android.restaurantreservations.base.view.BaseActivity;
import com.android.restaurantreservations.reservation.ReservationActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo - make CustomerFragment load first
        //show customers list
        //FragmentUtils.replaceFragment(this, new CustomerFragment(), R.id.fragment_main_container,
        //      false, CUSTOMER_FRAG_TAG);

        Intent intent = new Intent(MainActivity.this, ReservationActivity.class);
        startActivity(intent);
    }

}
