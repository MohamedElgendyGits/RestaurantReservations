package com.android.restaurantreservations.reservation.model.repo;

import com.android.restaurantreservations.reservation.model.entity.Reservation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mohamed Elgendy.
 */

public interface ReservationDataSource {

    Observable<List<Reservation>> getReservations();
    Observable<List<Reservation>> getOfflineReservations();
    void saveReservation(Reservation reservation);
    void updateReservation(String tableId);
    void deleteAllReservations();
}
