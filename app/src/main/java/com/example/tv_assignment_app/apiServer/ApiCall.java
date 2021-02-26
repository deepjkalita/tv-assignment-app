package com.example.tv_assignment_app.apiServer;

import com.example.tv_assignment_app.ModelClasses.CategoryModelClass;
import com.example.tv_assignment_app.ModelClasses.ConsultationModelClass;
import com.example.tv_assignment_app.ModelClasses.DoctorModelClass;
import com.example.tv_assignment_app.ModelClasses.UserModelClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiCall {

    //get request to get current user details
    @GET
    Call<List<UserModelClass>> getUser(@Url String url);

    //get doctors list by categories
    @GET
    Call<List<DoctorModelClass>> getDoctors(@Url String url);

    //get categories list
    @GET
    Call<List<CategoryModelClass>> getCategories(@Url String url);

    //get upcoming consultation
    @GET
    Call<List<ConsultationModelClass>> getUpcomingConsultation(@Url String url);

    //get past consultations
    Call<List<ConsultationModelClass>> getPastConsultation(@Url String url);

}
