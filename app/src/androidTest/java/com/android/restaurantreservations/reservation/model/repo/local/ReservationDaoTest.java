package com.android.restaurantreservations.reservation.model.repo.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.restaurantreservations.base.repo.local.AppDatabase;
import com.android.restaurantreservations.reservation.model.entity.Reservation;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Mohamed Elgendy.
 */

@RunWith(AndroidJUnit4.class)
public class ReservationDaoTest {

    private AppDatabase mDatabase;

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
    }


    @Test
    public void restReservationTest() {

        Reservation bookTable_10 = new Reservation(10,false);

        // Given that we have a book table number 10 in the data source
        mDatabase.reservationDao().addReservation(bookTable_10);

        // then we call reset all reservations
        mDatabase.reservationDao().resetReservations();

        // retrieve the reserved table
        Reservation retrievedReservation = mDatabase.reservationDao().getAllReservations().get(0);

        // assert that the status of the reserved table changed
        Assert.assertEquals(retrievedReservation.isTableStatus(),true);
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

}
