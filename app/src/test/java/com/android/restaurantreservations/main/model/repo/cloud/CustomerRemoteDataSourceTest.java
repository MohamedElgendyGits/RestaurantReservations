package com.android.restaurantreservations.main.model.repo.cloud;

import com.android.restaurantreservations.main.model.entity.Customer;
import com.android.restaurantreservations.main.model.repo.CustomerDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import io.reactivex.observers.TestObserver;

/**
 * Created by Mohamed Elgendy.
 */

public class CustomerRemoteDataSourceTest {

    private CustomerDataSource customerDataSource;

    @Before
    public void setup() {
        customerDataSource = CustomerRemoteDataSource.getInstance();
    }

    @Test
    public void getCustomersTest() throws Exception {
        TestObserver<List<Customer>> subscriber = TestObserver.create();
        customerDataSource.getCustomers().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertComplete();
    }
}
