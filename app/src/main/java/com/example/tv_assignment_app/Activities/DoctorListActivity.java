package com.example.tv_assignment_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.tv_assignment_app.Adapters.DoctorAdapter;
import com.example.tv_assignment_app.ModelClasses.DoctorModelClass;
import com.example.tv_assignment_app.R;
import com.example.tv_assignment_app.apiServer.ApiCall;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorListActivity extends AppCompatActivity   {

    private ArrayList<DoctorModelClass> docList;
    private DoctorAdapter docAdapter;
    private LinearLayoutManager docLayoutManager;

    private ApiCall apiCall;
    Retrofit retrofit;
    private String categoryId;
    private String categoryName;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_list);
        //intents from previous fragment
        categoryId=getIntent().getStringExtra("categoryId");
        categoryName=getIntent().getStringExtra("categoryName");

        //styling the top bar
        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.top_bar_gradient));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ActionBar actionBar = getActionBar();
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_gradient_shape));
        progressDialog=new ProgressDialog(this);
        docList=new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/")
                .build();

        doctorList();//call to server to get and load datas of available doctors into recycler view
    }

    private void doctorList() {
        progressDialog.setMessage("Loading, please wait");
        progressDialog.show();
        apiCall = retrofit.create(ApiCall.class);
        Call<List<DoctorModelClass>> call = apiCall
                .getDoctors("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/doctor/category/"+categoryId);
        call.enqueue(new Callback<List<DoctorModelClass>>() {
            @Override
            public void onResponse(Call<List<DoctorModelClass>> call, Response<List<DoctorModelClass>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<DoctorModelClass> doctors = response.body();
                Log.i("test",doctors.toString());

                //check to see if data is empty

                if(TextUtils.equals(doctors.toString(),"[]")){
                    progressDialog.dismiss();
                    Toast.makeText(DoctorListActivity.this, "Doctors not available on this category, please select dermatology only", Toast.LENGTH_LONG).show();
                }
                for (DoctorModelClass doctor : doctors) {
                    docList.add(new DoctorModelClass(doctor.getDoctor_id(),"Dr " +doctor.getDoctor_name(),doctor.getExperience()+" years experience",doctor.getDegree(),doctor.getNext_available(),doctor.getJob_role(),"Rs "+doctor.getPrice(),doctor.getDoctor_image(),doctor.getCategory_name()));
                    //Recycler View Code---
                    RecyclerView recyclerView =  findViewById(R.id.doctor_list_recycler_view);
                    docAdapter = new DoctorAdapter(docList);
                    //Layout Manager Code----
                    recyclerView.setHasFixedSize(true);
                    docLayoutManager = new LinearLayoutManager(DoctorListActivity.this);
                    recyclerView.setAdapter(docAdapter);
                    recyclerView.setLayoutManager(docLayoutManager);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<DoctorModelClass>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DoctorListActivity.this,"Error in Connection, Please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}