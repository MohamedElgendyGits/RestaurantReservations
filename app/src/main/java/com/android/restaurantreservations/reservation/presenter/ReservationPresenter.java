package com.android.restaurantreservations.reservation.presenter;

import com.android.restaurantreservations.base.presenter.BasePresenter;
import com.android.restaurantreservations.reservation.adapter.ReservationViewModel;

/**
 * Created by Mohamed Elgendy.
 */

public interface ReservationPresenter extends BasePresenter {

    void loadReservations();
    void tableClicked(ReservationViewModel reservationViewModel, int position);
    void updateTableStatus(ReservationViewModel reservationViewModel, int position);
}
