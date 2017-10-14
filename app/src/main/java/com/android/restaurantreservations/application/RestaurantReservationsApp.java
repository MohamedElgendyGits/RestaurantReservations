package com.android.restaurantreservations.application;

import android.app.Application;

import com.android.restaurantreservations.service.JobServiceManager;

/**
 * Created by Mohamed Elgendy.
 */

public class RestaurantReservationsApp extends Application {

    private static RestaurantReservationsApp instance;

    public static RestaurantReservationsApp getInstance() {
        if (instance == null){
            throw new IllegalStateException("Something went horribly wrong!!, no application attached!");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //schedule reset Reservation job service
        JobServiceManager.getInstance(this).scheduleResetJob();
    }
}
