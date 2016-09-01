package com.mathiasbrandt.firstimpressions.facebookobjects;

/**
 * Created by brandt on 01/09/2016.
 */

public class WorkExperience {
    private String id;
    private Employer employer;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
