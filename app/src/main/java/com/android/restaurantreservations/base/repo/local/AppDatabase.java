package com.android.restaurantreservations.base.repo.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.restaurantreservations.main.model.entity.Customer;
import com.android.restaurantreservations.main.model.repo.local.CustomerDao;
import com.android.restaurantreservations.reservation.model.entity.Reservation;
import com.android.restaurantreservations.reservation.model.repo.local.ReservationDao;

/**
 * Created by Mohamed Elgendy.
 */

@Database(entities = {Customer.class,  Reservation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "reservations_db").build();
        }
        return INSTANCE;
    }

    public static void destroyAppDatabaseInstance() {
        INSTANCE = null;
    }

    public abstract CustomerDao customerDao();
    public abstract ReservationDao reservationDao();
}
