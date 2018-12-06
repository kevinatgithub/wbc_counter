package com.bobongmd.app.a1cytewbcdifferentialcounter;

public class BloodCellCollection {

    private BloodCell[] cells;

    public BloodCellCollection(BloodCell[] cells) {
        this.cells = cells;
    }

    public BloodCell[] getCells() {
        return cells;
    }

    public void setCells(BloodCell[] cells) {
        this.cells = cells;
    }
}
