package com.android.restaurantreservations.reservation.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.application.RestaurantReservationsApp;
import com.android.restaurantreservations.reservation.adapter.ReservationViewModel;
import com.android.restaurantreservations.reservation.model.ReservationRepository;
import com.android.restaurantreservations.reservation.model.entity.Reservation;
import com.android.restaurantreservations.reservation.view.ReservationView;
import com.android.restaurantreservations.utils.ConnectionUtils;
import com.android.restaurantreservations.utils.TextUtils;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationPresenterImpl implements ReservationPresenter {

    private ReservationView reservationView;
    private ReservationRepository reservationRepository;
    private CompositeDisposable mCompositeDisposable;

    public ReservationPresenterImpl(ReservationView reservationView, ReservationRepository reservationRepository) {
        this.reservationView = reservationView;
        this.reservationRepository = reservationRepository;
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void loadReservations() {
        if (ConnectionUtils.isConnected(RestaurantReservationsApp.getInstance())) {
            retrieveFreshOrCachedReservations();
        } else {
            retrieveCachedReservationsOrShowErrorMessage();
        }
    }

    @Override
    public void tableClicked(ReservationViewModel reservationViewModel,int position) {
        if(reservationViewModel.isTableStatus()){
            reservationView.showBookingDialog(reservationViewModel, position);
        }else{
            reservationView.showInlineBookingError(TextUtils.getString(R.string.booking_error));
        }
    }

    @Override
    public void updateTableStatus(final ReservationViewModel reservationViewModel,int position) {
        reservationViewModel.setTableStatus(true);

        reservationRepository.updateReservation(
                String.valueOf(reservationViewModel.getTableId()));

        reservationView.updateGridStatus(reservationViewModel, position);
    }

    private void retrieveFreshOrCachedReservations() {

        reservationView.showProgressLoading();

        Disposable disposable = reservationRepository.getReservations()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Reservation>, Observable<ReservationViewModel>>() {
                    @Override
                    public Observable<ReservationViewModel> apply(@NonNull List<Reservation> reservations) throws Exception {
                        return getMap(reservations);
                    }
                }).toList().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ReservationViewModel>>() {
                    @Override
                    public void onNext(@NonNull List<ReservationViewModel> reservationViewModels) {
                        reservationView.loadReservationsList(reservationViewModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        reservationView.hideProgressLoading();
                        reservationView.showInlineError(TextUtils.getString(R.string.unknown_error));
                    }

                    @Override
                    public void onComplete() {
                        reservationView.hideProgressLoading();
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    private Observable<ReservationViewModel> getMap(@NonNull List<Reservation> reservations) {
        return Observable.fromIterable(reservations).map(new Function<Reservation, ReservationViewModel>() {
            @Override
            public ReservationViewModel apply(@NonNull Reservation reservation) throws Exception {
                return new ReservationViewModel(reservation.getId(),reservation.getTableNumber() ,reservation.isTableStatus());
            }
        });
    }

    private void retrieveCachedReservationsOrShowErrorMessage() {


        Disposable disposable = reservationRepository.getOfflineReservations()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Reservation>, Observable<ReservationViewModel>>() {
                    @Override
                    public Observable<ReservationViewModel> apply(@NonNull List<Reservation> reservations) throws Exception {
                        return getMap(reservations);
                    }
                }).toList().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ReservationViewModel>>() {
                    @Override
                    public void onNext(@NonNull List<ReservationViewModel> reservationViewModels) {
                        if(!reservationViewModels.isEmpty()){
                            reservationView.loadReservationsList(reservationViewModels);
                        }else {
                            reservationView.showInlineConnectionError(TextUtils.getString(R.string.connection_failed));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        reservationView.showInlineError(TextUtils.getString(R.string.unknown_error));
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
