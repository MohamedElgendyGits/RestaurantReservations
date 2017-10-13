package com.android.restaurantreservations.reservation.model.repo.local;

import android.content.Context;
import android.os.AsyncTask;

import com.android.restaurantreservations.base.repo.local.AppDatabase;
import com.android.restaurantreservations.reservation.model.entity.Reservation;
import com.android.restaurantreservations.reservation.model.repo.ReservationDataSource;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationLocalDataSource implements ReservationDataSource {

    private static ReservationLocalDataSource INSTANCE;
    private AppDatabase appDatabase;

    // Prevent direct instantiation.
    private ReservationLocalDataSource(Context context) {
        appDatabase = AppDatabase.getDatabase(context);
    }

    public static ReservationLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ReservationLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Reservation>> getReservations() {
        return Observable.fromCallable(new Callable<List<Reservation>>() {
            @Override
            public List<Reservation> call() throws Exception {
                return appDatabase.reservationDao().getAllReservations();
            }
        });
    }

    @Override
    public Observable<List<Reservation>> getOfflineReservations() {
        return Observable.fromCallable(new Callable<List<Reservation>>() {
            @Override
            public List<Reservation> call() throws Exception {
                return appDatabase.reservationDao().getAllReservations();
            }
        });
    }

    @Override
    public void saveReservation(Reservation reservation) {
        appDatabase.reservationDao().addReservation(reservation);
    }

    @Override
    public void updateReservation(String tableId) {
//        Observable.fromCallable(new Callable<Void>() {
//            @Override
//            public Void call() throws Exception {
//                appDatabase.reservationDao().updateReservation(reservation);
//                return null;
//            }
//        });

        new updateAsyncTask().execute(tableId);
    }

    @Override
    public void deleteAllReservations() {

        /*
        Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                appDatabase.reservationDao().resetReservations();
                return null;
            }
        });
        */

        new deleteAsyncTask().execute();
    }

    private class deleteAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            appDatabase.reservationDao().resetReservations();
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(final String... params) {
            appDatabase.reservationDao().updateReservation(params[0]);
            return null;
        }
    }
}

