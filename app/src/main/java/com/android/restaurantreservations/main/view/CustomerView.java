package com.android.restaurantreservations.main.view;

import com.android.restaurantreservations.base.view.BaseView;
import com.android.restaurantreservations.main.view.adapter.CustomerViewModel;

import java.util.List;

/**
 * Created by Mohamed Elgendy.
 */

public interface CustomerView extends BaseView {
    void loadCustomersList(List<CustomerViewModel> customerViewModels);
    void openReservationScreen();
    void filterCustomersList(List<CustomerViewModel> customerViewModels);
}
