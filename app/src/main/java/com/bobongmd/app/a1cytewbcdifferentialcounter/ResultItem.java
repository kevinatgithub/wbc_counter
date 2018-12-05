package com.bobongmd.app.a1cytewbcdifferentialcounter;

public class ResultItem {

    private String cell;
    private int countValue;
    private float percentage;
    private float absolute;

    public ResultItem(String cell, int countValue, float percentage, float absolute) {
        this.cell = cell;
        this.countValue = countValue;
        this.percentage = percentage;
        this.absolute = absolute;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public int getCountValue() {
        return countValue;
    }

    public void setCountValue(int countValue) {
        this.countValue = countValue;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getAbsolute() {
        return absolute;
    }

    public void setAbsolute(float absolute) {
        this.absolute = absolute;
    }
}
