package com.android.restaurantreservations.main.model.repo.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.restaurantreservations.base.repo.local.AppDatabase;
import com.android.restaurantreservations.main.model.entity.Customer;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Mohamed Elgendy.
 */

@RunWith(AndroidJUnit4.class)
public class CustomerDaoTest {

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
    public void insertAndGetCustomerById() {

        Customer customer = new Customer(2,"firstName","lastName");

        // Given that we have a customer in the data source
        mDatabase.customerDao().addCustomer(customer);

        Customer retrievedCustomer = mDatabase.customerDao().getCustomerById("2");

        // assert that the customer with specific id is similar to the one we added before
        Assert.assertEquals(customer.getFirstName(),retrievedCustomer.getFirstName());
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

}
