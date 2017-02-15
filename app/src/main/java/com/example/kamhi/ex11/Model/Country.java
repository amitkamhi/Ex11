package com.example.kamhi.ex11.Model;

import java.util.Comparator;

/**
 * Created by Kamhi on 20/1/2017.
 */

public class Country{
    private String name;
    private String flag;
    private String details;
    private String anthem;
    private String shortDetails;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAnthem() {
        return anthem;
    }

    public void setAnthem(String anthem) {this.anthem = anthem;}

    public String getShortDetails() {
        return shortDetails;
    }

    public void setShortDetails(String shortDetails) {
        this.shortDetails = shortDetails;
    }

    @Override
    public String toString() {
        return name;
    }


    public int compare(Country other) {
        return this.getName().compareTo(other.getName());
    }
}
