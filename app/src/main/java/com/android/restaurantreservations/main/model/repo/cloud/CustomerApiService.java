package com.android.restaurantreservations.main.model.repo.cloud;

import com.android.restaurantreservations.main.model.entity.Customer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Mohamed Elgendy.
 */

public interface CustomerApiService {

    @GET("customer-list.json")
    Observable<List<Customer>> getCustomers();
}
