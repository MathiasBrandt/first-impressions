package com.mathiasbrandt.firstimpressions.facebookobjects;

import java.util.List;

/**
 * Created by brandt on 01/09/2016.
 */

public class EducationExperience {
    private String id;
    private School school;
    private String type;
    private Year year;
    private Degree degree;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }
}
