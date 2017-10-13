package com.android.restaurantreservations.reservation.model.repo.cloud;

import java.util.ArrayList;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Mohamed Elgendy.
 */

public interface ReservationApiService {

    @GET("table-map.json")
    Observable<ArrayList<Boolean>> getReservations();
}
