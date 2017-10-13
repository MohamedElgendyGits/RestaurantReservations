package com.android.restaurantreservations.main.presenter;

import com.android.restaurantreservations.base.presenter.BasePresenter;

/**
 * Created by Mohamed Elgendy.
 */

public interface CustomerPresenter extends BasePresenter {
    void loadCustomers();
    void showReservationScreen();
    void searchByCustomer(String query);
}
