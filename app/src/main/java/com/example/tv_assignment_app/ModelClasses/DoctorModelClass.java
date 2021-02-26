package com.example.tv_assignment_app.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class DoctorModelClass {
    int doctor_id;
    String doctor_name,experience,degree,next_available,job_role,price,doctor_image,category_name;

    public DoctorModelClass(int doctor_id, String doctor_name, String experience, String degree, String next_available, String job_role, String price,String doctor_image,String category_name) {
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.experience = experience;
        this.degree = degree;
        this.next_available = next_available;
        this.job_role = job_role;
        this.price = price;
        this.doctor_image=doctor_image;
        this.category_name=category_name;
    }

    @SerializedName("body")

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getNext_available() {
        return next_available;
    }

    public void setNext_available(String next_available) {
        this.next_available = next_available;
    }

    public String getJob_role() {
        return job_role;
    }

    public void setJob_role(String job_role) {
        this.job_role = job_role;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDoctor_image() {
        return doctor_image;
    }

    public void setDoctor_image(String doctor_image) {
        this.doctor_image = doctor_image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
