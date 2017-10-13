package com.android.restaurantreservations.main.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.application.RestaurantReservationsApp;
import com.android.restaurantreservations.base.repo.cloud.RetrofitClient;
import com.android.restaurantreservations.main.model.CustomerRepository;
import com.android.restaurantreservations.main.model.entity.Customer;
import com.android.restaurantreservations.main.model.repo.cloud.CustomerApiService;
import com.android.restaurantreservations.main.view.CustomerView;
import com.android.restaurantreservations.main.view.adapter.CustomerViewModel;
import com.android.restaurantreservations.utils.ConnectionUtils;
import com.android.restaurantreservations.utils.TextUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mohamed Elgendy.
 */

public class CustomerPresenterImpl implements CustomerPresenter {

    private CustomerView customerView;
    private CustomerRepository customerRepository;
    private CompositeDisposable mCompositeDisposable;

    public CustomerPresenterImpl(CustomerView customerView, CustomerRepository customerRepository) {
        this.customerView = customerView;
        this.customerRepository = customerRepository;
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void loadCustomers() {

        if (ConnectionUtils.isConnected(RestaurantReservationsApp.getInstance())) {
            retrieveFreshOrCachedCustomers();
        } else {
            retrieveCachedCustomersOrShowErrorMessage();
        }
    }

    @Override
    public void showReservationScreen() {
        customerView.openReservationScreen();
    }

    @Override
    public void searchByCustomer(String query) {
        //todo searchByCustomer
    }

    private void retrieveFreshOrCachedCustomers() {

        customerView.showProgressLoading();

        Disposable disposable = customerRepository.getCustomers()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Customer>, Observable<CustomerViewModel>>() {
                    @Override
                    public Observable<CustomerViewModel> apply(@NonNull List<Customer> customers) throws Exception {
                        return getMap(customers);
                    }
                }).toList().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<CustomerViewModel>>() {
                    @Override
                    public void onNext(@NonNull List<CustomerViewModel> customers) {
                        customerView.loadCustomersList(customers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        customerView.hideProgressLoading();
                        customerView.showInlineError(TextUtils.getString(R.string.unknown_error));
                    }

                    @Override
                    public void onComplete() {
                        customerView.hideProgressLoading();
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    private Observable<CustomerViewModel> getMap(@NonNull List<Customer> customers) {
        return Observable.fromIterable(customers).map(new Function<Customer, CustomerViewModel>() {
            @Override
            public CustomerViewModel apply(@NonNull Customer customer) throws Exception {
                return new CustomerViewModel(customer.getFirstName(), customer.getLastName());
            }
        });
    }

    private void retrieveCachedCustomersOrShowErrorMessage() {

        Disposable disposable = customerRepository.getOfflineCustomers()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Customer>, Observable<CustomerViewModel>>() {
                    @Override
                    public Observable<CustomerViewModel> apply(@NonNull List<Customer> customers) throws Exception {
                        return getMap(customers);
                    }
                }).toList().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<CustomerViewModel>>() {
                    @Override
                    public void onNext(@NonNull List<CustomerViewModel> customers) {
                        if(!customers.isEmpty()){
                            customerView.loadCustomersList(customers);
                        }else {
                            customerView.showInlineConnectionError(TextUtils.getString(R.string.connection_failed));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        customerView.showInlineError(TextUtils.getString(R.string.unknown_error));
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clearRxDisposables() {
        mCompositeDisposable.clear();
    }
}
