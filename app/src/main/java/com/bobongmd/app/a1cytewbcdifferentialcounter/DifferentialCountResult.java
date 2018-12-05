package com.bobongmd.app.a1cytewbcdifferentialcounter;

public class DifferentialCountResult {

    private BloodCell bloodCell;
    private int countValue;

    public DifferentialCountResult(BloodCell bloodCell, int countValue) {
        this.bloodCell = bloodCell;
        this.countValue = countValue;
    }

    public BloodCell getBloodCell() {
        return bloodCell;
    }

    public void setBloodCell(BloodCell bloodCell) {
        this.bloodCell = bloodCell;
    }

    public int getCountValue() {
        return countValue;
    }

    public void setCountValue(int countValue) {
        this.countValue = countValue;
    }
}
