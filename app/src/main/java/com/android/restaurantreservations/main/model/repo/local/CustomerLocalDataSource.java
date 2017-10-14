package com.android.restaurantreservations.main.model.repo.local;

import android.content.Context;

import com.android.restaurantreservations.base.repo.local.AppDatabase;
import com.android.restaurantreservations.main.model.entity.Customer;
import com.android.restaurantreservations.main.model.repo.CustomerDataSource;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by Mohamed Elgendy.
 */

public class CustomerLocalDataSource implements CustomerDataSource {

    private static CustomerLocalDataSource INSTANCE;
    private AppDatabase appDatabase;

    // Prevent direct instantiation.
    private CustomerLocalDataSource(Context context) {
        appDatabase = AppDatabase.getDatabase(context);
    }

    public static CustomerLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Customer>> getCustomers() {
        return Observable.fromCallable(new Callable<List<Customer>>() {
            @Override
            public List<Customer> call() throws Exception {
                return appDatabase.customerDao().getAllCustomers();
            }
        });
    }

    @Override
    public Observable<List<Customer>> getOfflineCustomers() {
        return Observable.fromCallable(new Callable<List<Customer>>() {
            @Override
            public List<Customer> call() throws Exception {
                return appDatabase.customerDao().getAllCustomers();
            }
        });
    }

    @Override
    public void saveCustomer(final Customer customer) {
        appDatabase.customerDao().addCustomer(customer);
    }

    @Override
    public Observable<List<Customer>> searchByCustomer(final String query) {
        return Observable.fromCallable(new Callable<List<Customer>>() {
            @Override
            public List<Customer> call() throws Exception {
                return appDatabase.customerDao().search(query);
            }
        });
    }
}
