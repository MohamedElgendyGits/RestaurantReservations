package com.android.restaurantreservations.reservation.model;

import com.android.restaurantreservations.reservation.model.entity.Reservation;
import com.android.restaurantreservations.reservation.model.repo.ReservationDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationRepository implements ReservationDataSource {

    private static ReservationRepository INSTANCE = null;

    private final ReservationDataSource reservationRemoteDataSource;

    private final ReservationDataSource reservationLocalDataSource;


    // Prevent direct instantiation.
    private ReservationRepository(ReservationDataSource reservationRemoteDataSource,
                                  ReservationDataSource reservationLocalDataSource) {
        this.reservationRemoteDataSource = reservationRemoteDataSource;
        this.reservationLocalDataSource = reservationLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param reservationRemoteDataSource the backend data source
     * @param reservationLocalDataSource  the device storage data source
     * @return the {@link ReservationRepository} instance
     */
    public static ReservationRepository getInstance(ReservationDataSource reservationRemoteDataSource,
                                                 ReservationDataSource reservationLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ReservationRepository(reservationRemoteDataSource, reservationLocalDataSource);
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
     * Gets reservations local data source (room) or remote data source, whichever is
     * available first.
     */
    @Override
    public Observable<List<Reservation>> getReservations() {

        Observable<List<Reservation>> localReservations = getCachedLocalReservations();
        List<Reservation> cachedList = localReservations.subscribeOn(Schedulers.io()).blockingFirst();
        if(cachedList.isEmpty()){
            return getAndSaveRemoteReservations();
        }else{
            return localReservations;
        }
    }

    @Override
    public Observable<List<Reservation>> getOfflineReservations() {
        return getCachedLocalReservations();
    }

    @Override
    public void saveReservation(Reservation reservation) {
        reservationLocalDataSource.saveReservation(reservation);
    }

    @Override
    public void updateReservation(String tableId) {
        reservationLocalDataSource.updateReservation(tableId);
    }

    @Override
    public void deleteAllReservations() {
        reservationLocalDataSource.deleteAllReservations();
    }

    private Observable<List<Reservation>> getCachedLocalReservations() {
        return reservationLocalDataSource.getReservations();
    }

    private Observable<List<Reservation>> getAndSaveRemoteReservations() {

        return reservationRemoteDataSource.getReservations()
                .flatMap(new Function<List<Reservation>, Observable<Reservation>>() {
                    @Override
                    public Observable<Reservation> apply(@NonNull List<Reservation> reservations) throws Exception {
                        return Observable.fromIterable(reservations);
                    }
                }).doOnNext(new Consumer<Reservation>() {
                    @Override
                    public void accept(Reservation reservation) throws Exception {
                        saveReservation(reservation);
                    }
                }).toList().toObservable();
    }
}
