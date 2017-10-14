package com.android.restaurantreservations.reservation.model.repo.cloud;

import com.android.restaurantreservations.reservation.model.entity.Reservation;
import com.android.restaurantreservations.reservation.model.repo.ReservationDataSource;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.observers.TestObserver;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationRemoteDataSourceTest {

    private ReservationDataSource reservationDataSource;

    @Before
    public void setup() {
        reservationDataSource = ReservationRemoteDataSource.getInstance();
    }

    @Test
    public void getReservationsTest() throws Exception {
        TestObserver<List<Reservation>> subscriber = TestObserver.create();
        reservationDataSource.getReservations().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertComplete();
    }
}
