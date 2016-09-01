package com.mathiasbrandt.firstimpressions.facebookobjects;

/**
 * Created by brandt on 01/09/2016.
 */

public class WorkExperience {
    private String id;
    private Node location;
    private Node employer;
    private Node position;

    public Node getLocation() {
        return location;
    }

    public void setLocation(Node location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Node getEmployer() {
        return employer;
    }

    public void setEmployer(Node employer) {
        this.employer = employer;
    }

    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }
}
