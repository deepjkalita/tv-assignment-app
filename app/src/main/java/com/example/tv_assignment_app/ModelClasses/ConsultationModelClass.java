package com.example.tv_assignment_app.ModelClasses;

import com.example.tv_assignment_app.Adapters.CategoryAdapter;
import com.google.gson.annotations.SerializedName;

public class ConsultationModelClass  {
    String doctor_name,category_name,doctor_image,date,time;

    public ConsultationModelClass(String doctor_name, String category_name, String doctor_image,String date,String time) {
        this.doctor_name = doctor_name;
        this.category_name = category_name;
        this.doctor_image=doctor_image;
        this.date=date;
        this.time=time;
    }
    @SerializedName("body")

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDoctor_image() {
        return doctor_image;
    }

    public void setDoctor_image(String doctor_image) {
        this.doctor_image = doctor_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
