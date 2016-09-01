package com.mathiasbrandt.firstimpressions.facebookobjects;

import com.google.android.gms.auth.account.WorkAccount;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    private String age;
    @SerializedName("birthday")
    private String birthMonth;
    @SerializedName("id")
    private String facebookId;
    private Node location;
    private String name;
    private String photoUrl;
    private List<WorkExperience> work;
    private List<EducationExperience> education;

    public List<WorkExperience> getWork() {
        return work;
    }

    public void setWork(List<WorkExperience> work) {
        this.work = work;
    }

    public List<EducationExperience> getEducation() {
        return education;
    }

    public void setEducation(List<EducationExperience> education) {
        this.education = education;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public Node getLocation() {
        return location;
    }

    public void setLocation(Node location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
