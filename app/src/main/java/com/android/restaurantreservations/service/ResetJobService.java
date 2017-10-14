package com.android.restaurantreservations.service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.restaurantreservations.application.RestaurantReservationsApp;
import com.android.restaurantreservations.base.injector.Injection;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Mohamed Elgendy.
 */

public class ResetJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {

        AsyncTask mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Injection.provideReservationRepository
                        (RestaurantReservationsApp.getInstance()).deleteAllReservations();

                RxBus.publish("");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
            }
        };

        mBackgroundTask.execute();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false; //no need to run it again if job failed
    }
}
