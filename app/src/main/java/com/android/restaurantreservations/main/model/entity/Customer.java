package com.android.restaurantreservations.main.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy.
 */

@Entity(tableName = "customers")
public class Customer {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("customerFirstName")
    @ColumnInfo(name = "first_name")
    private String firstName;

    @SerializedName("customerLastName")
    @ColumnInfo(name = "last_name")
    private String lastName;

    public Customer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
