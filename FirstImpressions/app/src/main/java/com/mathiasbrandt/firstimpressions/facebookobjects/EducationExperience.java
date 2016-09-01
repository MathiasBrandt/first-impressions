package com.mathiasbrandt.firstimpressions.facebookobjects;

import java.util.List;

/**
 * Created by brandt on 01/09/2016.
 */

public class EducationExperience {
    private String id;
    private Node school;
    private String type;
    private Node year;
    private Node degree;

    public Node getSchool() {
        return school;
    }

    public void setSchool(Node school) {
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getYear() {
        return year;
    }

    public void setYear(Node year) {
        this.year = year;
    }

    public Node getDegree() {
        return degree;
    }

    public void setDegree(Node degree) {
        this.degree = degree;
    }

}
