package com.android.restaurantreservations.main.model.repo.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Observable;

import com.android.restaurantreservations.main.model.entity.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Elgendy.
 */

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customers")
    List<Customer> getAllCustomers();

    @Query("select * from customers where id = :id")
    Customer getCustomerById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCustomer(Customer customer);

    @Insert
    void insertAllCustomers(List<Customer> customers);

    @Delete
    void delete(Customer customer);

    @Query("delete from customers")
    void deleteAllCustomers();
}
