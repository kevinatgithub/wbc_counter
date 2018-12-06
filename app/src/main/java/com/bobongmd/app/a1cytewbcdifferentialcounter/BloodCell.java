package com.bobongmd.app.a1cytewbcdifferentialcounter;

public class BloodCell {

    private String name;
    private String shortname;
    private String img;

    public BloodCell(String name, String shortname, String img) {
        this.name = name;
        this.shortname = shortname;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
