package com.android.restaurantreservations.reservation.model.repo.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.restaurantreservations.reservation.model.entity.Reservation;

import java.util.List;

/**
 * Created by Mohamed Elgendy.
 */

@Dao
public interface ReservationDao {

    @Query("SELECT * FROM reservations")
    List<Reservation> getAllReservations();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addReservation(Reservation reservation);

    @Query("update reservations set table_status = 0 where id = :tableId")
    void updateReservation(String tableId);

    @Query("update reservations set table_status = 1")
    void resetReservations();
}
