package com.example.tv_assignment_app.ModelClasses;


import com.google.gson.annotations.SerializedName;

public class CategoryModelClass {
    String category_name,category_image_url;
    int category_id;

    public CategoryModelClass(String category_name, String category_image_url, int category_id) {
        this.category_name = category_name;
        this.category_image_url = category_image_url;
        this.category_id = category_id;
    }

    @SerializedName("body")

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}

