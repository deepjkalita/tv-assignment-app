package com.example.tv_assignment_app.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class UserModelClass {
    int user_id;
    String user_name,user_image,next_appointment_date,next_appointment_time;

    public UserModelClass(int user_id, String user_name, String user_image, String next_appointment_date, String next_appointment_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_image = user_image;
        this.next_appointment_date = next_appointment_date;
        this.next_appointment_time = next_appointment_time;
    }

    @SerializedName("body")

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getNext_appointment_date() {
        return next_appointment_date;
    }

    public void setNext_appointment_date(String next_appointment_date) {
        this.next_appointment_date = next_appointment_date;
    }

    public String getNext_appointment_time() {
        return next_appointment_time;
    }

    public void setNext_appointment_time(String next_appointment_time) {
        this.next_appointment_time = next_appointment_time;
    }
}
