package com.android.restaurantreservations.main.model;

import android.util.Log;

import com.android.restaurantreservations.main.model.entity.Customer;
import com.android.restaurantreservations.main.model.repo.CustomerDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mohamed Elgendy.
 */

public class CustomerRepository implements CustomerDataSource {

    private static CustomerRepository INSTANCE = null;

    private final CustomerDataSource customerRemoteDataSource;

    private final CustomerDataSource customerLocalDataSource;


    // Prevent direct instantiation.
    private CustomerRepository(CustomerDataSource customerRemoteDataSource,
                               CustomerDataSource customerLocalDataSource) {
        this.customerRemoteDataSource = customerRemoteDataSource;
        this.customerLocalDataSource = customerLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param customerRemoteDataSource the backend data source
     * @param customerLocalDataSource  the device storage data source
     * @return the {@link CustomerRepository} instance
     */
    public static CustomerRepository getInstance(CustomerDataSource customerRemoteDataSource,
                                                 CustomerDataSource customerLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerRepository(customerRemoteDataSource, customerLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force create a new instance
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    /**
     * Gets customers local data source (room) or remote data source, whichever is
     * available first.
     */
    @Override
    public Observable<List<Customer>> getCustomers() {

        Observable<List<Customer>> localCustomers = getCachedLocalCustomers();
        List<Customer> cachedList = localCustomers.subscribeOn(Schedulers.io()).blockingFirst();
        if (cachedList.isEmpty()) {
            return getAndSaveRemoteCustomers();
        } else {
            return localCustomers;
        }
    }

    @Override
    public Observable<List<Customer>> getOfflineCustomers() {
        return getCachedLocalCustomers();
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerLocalDataSource.saveCustomer(customer);
    }

    private Observable<List<Customer>> getCachedLocalCustomers() {
        return customerLocalDataSource.getCustomers();
    }

    private Observable<List<Customer>> getAndSaveRemoteCustomers() {

        return customerRemoteDataSource.getCustomers()
                .flatMap(new Function<List<Customer>, Observable<Customer>>() {
                    @Override
                    public Observable<Customer> apply(@NonNull List<Customer> customers) throws Exception {
                        return Observable.fromIterable(customers);
                    }
                }).doOnNext(new Consumer<Customer>() {
                    @Override
                    public void accept(Customer customer) throws Exception {
                        saveCustomer(customer);
                    }
                }).toList().toObservable();
    }
}
