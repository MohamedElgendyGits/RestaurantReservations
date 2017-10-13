package com.android.restaurantreservations.reservation.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Mohamed Elgendy.
 */

@Entity(tableName = "reservations")
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "table_number")
    private int tableNumber;

    @ColumnInfo(name = "table_status")
    private boolean tableStatus;

    public Reservation(int tableNumber, boolean tableStatus) {
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(boolean tableStatus) {
        this.tableStatus = tableStatus;
    }
}
