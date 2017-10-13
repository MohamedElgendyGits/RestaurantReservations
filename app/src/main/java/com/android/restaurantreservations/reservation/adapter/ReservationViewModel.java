package com.android.restaurantreservations.reservation.adapter;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationViewModel {

    private int tableId;
    private int tableNumber;
    private boolean tableStatus;

    public ReservationViewModel(int tableId, int tableNumber, boolean tableStatus) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
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
