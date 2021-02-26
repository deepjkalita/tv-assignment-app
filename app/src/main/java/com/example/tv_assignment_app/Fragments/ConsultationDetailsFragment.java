package com.example.tv_assignment_app.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv_assignment_app.Activities.DoctorListActivity;
import com.example.tv_assignment_app.Adapters.CategoryAdapter;
import com.example.tv_assignment_app.Adapters.ConsultationAdapter;
import com.example.tv_assignment_app.ModelClasses.CategoryModelClass;
import com.example.tv_assignment_app.ModelClasses.ConsultationModelClass;
import com.example.tv_assignment_app.R;
import com.example.tv_assignment_app.apiServer.ApiCall;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationDetailsFragment extends Fragment implements CategoryAdapter.OnItemClickListener  {

    //declarations to get past consultations into recycler view
    private ArrayList<ConsultationModelClass> consultList;
    private ConsultationAdapter consultAdapter;
    private LinearLayoutManager consultLayoutManager;

    //declarations to get categories into recycler view
    private ArrayList<CategoryModelClass> categoryList;
    private CategoryAdapter categoryAdapter;

    //upcoming consultations declarations
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView amTextView;

    private ApiCall apiCall;
    Retrofit retrofit;
    private ProgressDialog progressDialog;
    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=  inflater.inflate(R.layout.fragment_consultation_details, container, false);
        consultList=new ArrayList<>();
        categoryList=new ArrayList<>();

        dateTextView=view.findViewById(R.id.consultation_date);
        timeTextView=view.findViewById(R.id.consultation_time);

        amTextView=view.findViewById(R.id.am);
        userId=getArguments().getString("userId");//got as string bundle from previous activity
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading, please wait");
        progressDialog.show();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/")
                .build();

        apiCall = retrofit.create(ApiCall.class);

        upcomingConsultationsBox(); //function to load the datas of upcoming consultation
        pastConsultations(view); //function to load the datas of past consultation
        categories(view); //function to load categories

        return view;
    }

    private void categories(final View view) {
        apiCall = retrofit.create(ApiCall.class);
        //get booking list
        Call<List<CategoryModelClass>> call = apiCall
                .getCategories("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/categories");
        call.enqueue(new Callback<List<CategoryModelClass>>() {
            @Override
            public void onResponse(Call<List<CategoryModelClass>> call, Response<List<CategoryModelClass>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<CategoryModelClass> categories = response.body();
                for (CategoryModelClass category : categories) {
                    categoryList.add(new CategoryModelClass(category.getCategory_name(),category.getCategory_image_url(),category.getCategory_id()));
                    RecyclerView recyclerViewCategories=(RecyclerView) view.findViewById(R.id.categories_recycler_view);
                    categoryAdapter=new CategoryAdapter(categoryList);
                    recyclerViewCategories.setHasFixedSize(true);
                    categoryAdapter.setOnItemClickListener(ConsultationDetailsFragment.this);
                    RecyclerView.LayoutManager categoryLayoutManager= new GridLayoutManager(getContext(), 2);
                    recyclerViewCategories.setAdapter(categoryAdapter);
                    recyclerViewCategories.setLayoutManager(categoryLayoutManager);
                }
            }
            @Override
            public void onFailure(Call<List<CategoryModelClass>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error in Connection, Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pastConsultations(final View view) {
        Call<List<ConsultationModelClass>> call = apiCall
                .getUpcomingConsultation("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/consultation/pastConsultation/"+userId);
        call.enqueue(new Callback<List<ConsultationModelClass>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<ConsultationModelClass>> call, Response<List<ConsultationModelClass>> response) {
                if (!response.isSuccessful()) {

                    return;
                }
                List<ConsultationModelClass> consultaions = response.body();
                for (ConsultationModelClass consultation : consultaions) {
                    //adding to recycler view
                    Log.i("cat",consultation.getDoctor_name());
                    consultList.add(new ConsultationModelClass(consultation.getDoctor_name(),consultation.getCategory_name(),consultation.getDoctor_image(),consultation.getDate(),consultation.getTime()));
                    RecyclerView recyclerView =  view.findViewById(R.id.consultations_recycler_view);
                    consultAdapter = new ConsultationAdapter(consultList);
                    recyclerView.setHasFixedSize(true);
                    consultLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setAdapter(consultAdapter);
                    recyclerView.setLayoutManager(consultLayoutManager);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<ConsultationModelClass>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error in Connection, Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void upcomingConsultationsBox() {
        Call<List<ConsultationModelClass>> call = apiCall
                .getUpcomingConsultation("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/consultation/upcoming/"+userId);
        call.enqueue(new Callback<List<ConsultationModelClass>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<ConsultationModelClass>> call, Response<List<ConsultationModelClass>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<ConsultationModelClass> schedules = response.body();
                for (ConsultationModelClass schedule : schedules) {
                    //cutting and concat of date fields to get date in desired form
                    String date=schedule.getDate().substring(0,10);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = null;
                    try {
                        parse = sdf.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(parse);
                    String finalDateString=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.ENGLISH).substring(0,3)
                            +", "+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.ENGLISH);
                    dateTextView.setText(finalDateString);
                    timeTextView.setText(schedule.getTime().substring(0,5)); //added date fields into required views
                    amTextView.setText(schedule.getTime().substring(6,8));
                }
            }
            @Override
            public void onFailure(Call<List<ConsultationModelClass>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error in Connection, Please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        //click listener for categories recycler view
        CategoryModelClass clickedItem=categoryList.get(position);
        String categoryId=String.valueOf(clickedItem.getCategory_id());
        Intent intent=new Intent(getContext(), DoctorListActivity.class);
        intent.putExtra("categoryId",categoryId);
        intent.putExtra("categoryName",clickedItem.getCategory_name());
        startActivity(intent);
    }
}
