package com.android.restaurantreservations.reservation.model.repo.cloud;

import com.android.restaurantreservations.base.repo.cloud.RetrofitClient;
import com.android.restaurantreservations.reservation.model.entity.Reservation;
import com.android.restaurantreservations.reservation.model.repo.ReservationDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationRemoteDataSource implements ReservationDataSource {

    private static ReservationRemoteDataSource INSTANCE;
    private final ReservationApiService reservationApiService;

    // Prevent direct instantiation.
    private ReservationRemoteDataSource() {
        reservationApiService = RetrofitClient.getClient().create(ReservationApiService.class);
    }

    public static ReservationRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Reservation>> getReservations() {
        return reservationApiService.getReservations().flatMap(new Function<List<Boolean>, Observable<Reservation>>() {
            @Override
            public Observable<Reservation> apply(@NonNull List<Boolean> reservationResponses) throws Exception {
                return getMap(reservationResponses);
            }
        }).toList().toObservable();
    }

    private Observable<Reservation> getMap(@NonNull List<Boolean> reservationResponses) {
        // assign table numbers
        List<Reservation> reservations = new ArrayList<>();
        for(int i=0; i<reservationResponses.size(); i++){
            reservations.add(new Reservation(i,reservationResponses.get(i)));
        }

        return Observable.fromIterable(reservations);
    }

    @Override
    public Observable<List<Reservation>> getOfflineReservations() {
        return null;
        /* Not required for the remote data source because the reservation repository
         handle this method to the local data source
         */
    }

    @Override
    public void saveReservation(Reservation reservation) {
        /* Not required for the remote data source because the reservation repository
         handle this method to the local data source
         */
    }

    @Override
    public void updateReservation(String tableId) {
        /* Not required for the remote data source because the reservation repository
         handle this method to the local data source
         */
    }

    @Override
    public void deleteAllReservations() {
        /* Not required for the remote data source because the reservation repository
         handle this method to the local data source
         */
    }
}

