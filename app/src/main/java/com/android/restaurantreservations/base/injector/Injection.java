package com.android.restaurantreservations.base.injector;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.restaurantreservations.main.model.CustomerRepository;
import com.android.restaurantreservations.main.model.repo.cloud.CustomerRemoteDataSource;
import com.android.restaurantreservations.main.model.repo.local.CustomerLocalDataSource;
import com.android.restaurantreservations.reservation.model.ReservationRepository;
import com.android.restaurantreservations.reservation.model.repo.cloud.ReservationRemoteDataSource;
import com.android.restaurantreservations.reservation.model.repo.local.ReservationLocalDataSource;

/**
 * Created by Mohamed Elgendy.
 */

public class Injection {

    /**
     * Enables injection of mock implementations for
     * {@link CustomerRepository} at compile time. This is useful to isolate the dependencies.
     */
    public static CustomerRepository provideCustomerRepository(@NonNull Context context) {
        return CustomerRepository.getInstance(CustomerRemoteDataSource.getInstance(),
                CustomerLocalDataSource.getInstance(context));
    }

    /**
     * Enables injection of mock implementations for
     * {@link ReservationRepository} at compile time. This is useful to isolate the dependencies.
     */
    public static ReservationRepository provideReservationRepository(@NonNull Context context) {
        return ReservationRepository.getInstance(ReservationRemoteDataSource.getInstance(),
                ReservationLocalDataSource.getInstance(context));
    }
}
