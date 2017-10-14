package com.android.restaurantreservations.main.model.repo.cloud;

import com.android.restaurantreservations.base.repo.cloud.RetrofitClient;
import com.android.restaurantreservations.main.model.entity.Customer;
import com.android.restaurantreservations.main.model.repo.CustomerDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mohamed Elgendy.
 */

public class CustomerRemoteDataSource implements CustomerDataSource {

    private static CustomerRemoteDataSource INSTANCE;
    private final CustomerApiService customerApiService;

    // Prevent direct instantiation.
    private CustomerRemoteDataSource() {
        customerApiService = RetrofitClient.getClient().create(CustomerApiService.class);
    }

    public static CustomerRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Customer>> getCustomers() {
        return customerApiService.getCustomers();
    }

    @Override
    public Observable<List<Customer>> getOfflineCustomers() {
        return null;
        /* Not required for the remote data source because the customer repository
         handle this method to the local data source
         */
    }

    @Override
    public void saveCustomer(Customer customer) {
        /* Not required for the remote data source because the customer repository
         handle this method to the local data source
         */
    }

    @Override
    public Observable<List<Customer>> searchByCustomer(String query) {
        return null;
        /* Not required for the remote data source because the customer repository
         handle this method to the local data source
         */
    }
}
