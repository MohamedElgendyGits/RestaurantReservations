package com.android.restaurantreservations.reservation.view;

import com.android.restaurantreservations.base.view.BaseView;
import com.android.restaurantreservations.reservation.adapter.ReservationViewModel;

import java.util.List;

/**
 * Created by Mohamed Elgendy.
 */

public interface ReservationView extends BaseView {
    void loadReservationsList(List<ReservationViewModel> reservations);
    void showBookingDialog(ReservationViewModel reservationViewModel, int position);
    void showInlineBookingError(String error);
    void updateGridStatus(ReservationViewModel reservationViewModel,int position);
    void clearAllGridStatus();
}
