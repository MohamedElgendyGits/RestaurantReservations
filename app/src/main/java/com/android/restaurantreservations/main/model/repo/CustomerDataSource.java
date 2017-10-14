package com.android.restaurantreservations.main.model.repo;


import com.android.restaurantreservations.main.model.entity.Customer;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Mohamed Elgendy.
 */

public interface CustomerDataSource {

    Observable<List<Customer>> getCustomers();
    Observable<List<Customer>> getOfflineCustomers();
    void saveCustomer(Customer customer);
    Observable<List<Customer>> searchByCustomer(String query);
}
