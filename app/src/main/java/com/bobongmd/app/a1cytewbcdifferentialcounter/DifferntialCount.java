package com.bobongmd.app.a1cytewbcdifferentialcounter;

import java.util.ArrayList;

public class DifferntialCount {

    private String typeOfCounter;
    private String cellsToBeCounted;
    private DifferentialCountResult[] cellsForCounting;

    public DifferntialCount(String typeOfCounter, String cellsToBeCounted, DifferentialCountResult[] cellsForCounting) {
        this.typeOfCounter = typeOfCounter;
        this.cellsToBeCounted = cellsToBeCounted;
        this.cellsForCounting = cellsForCounting;
    }

    public String getTypeOfCounter() {
        return typeOfCounter;
    }

    public void setTypeOfCounter(String typeOfCounter) {
        this.typeOfCounter = typeOfCounter;
    }

    public String getCellsToBeCounted() {
        return cellsToBeCounted;
    }

    public void setCellsToBeCounted(String cellsToBeCounted) {
        this.cellsToBeCounted = cellsToBeCounted;
    }

    public DifferentialCountResult[] getCellsForCounting() {
        return cellsForCounting;
    }

    public void setCellsForCounting(DifferentialCountResult[] cellsForCounting) {
        this.cellsForCounting = cellsForCounting;
    }
}
