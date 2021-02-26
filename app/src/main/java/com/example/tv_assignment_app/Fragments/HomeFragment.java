package com.example.tv_assignment_app.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tv_assignment_app.Activities.DoctorListActivity;
import com.example.tv_assignment_app.Adapters.CategoryAdapter;
import com.example.tv_assignment_app.ModelClasses.CategoryModelClass;
import com.example.tv_assignment_app.R;
import com.example.tv_assignment_app.apiServer.ApiCall;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements CategoryAdapter.OnItemClickListener {

    //parameters to load values into recycler view
    private ArrayList<CategoryModelClass> categorylist;
    private CategoryAdapter categoryAdapter;

    private ApiCall apiCall;
    Retrofit retrofit;

    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=  inflater.inflate(R.layout.fragment_home, container, false);
        categorylist=new ArrayList<>();
        progressDialog=new ProgressDialog(getContext());

       retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
               .baseUrl("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/")
                .build();

       categories( view);//function to call the server and load the categories into recycler view

        return view;
    }

    private void categories(final View view) {
        progressDialog.setMessage("Loading, please wait");
        progressDialog.show();
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
                    //adding to recycler view
                    Log.i("img",category.getCategory_image_url());
                    categorylist.add(new CategoryModelClass(category.getCategory_name(),category.getCategory_image_url(),category.getCategory_id()));
                    //Recycler View Code---
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
                    categoryAdapter = new CategoryAdapter(categorylist);
                    //Layout Manager Code----
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager  mLayoutManager = new GridLayoutManager(getContext(), 2);
                    categoryAdapter.setOnItemClickListener(HomeFragment.this);
                    recyclerView.setAdapter(categoryAdapter);
                    recyclerView.setLayoutManager(mLayoutManager);
                    progressDialog.dismiss();


                }
            }
            @Override
            public void onFailure(Call<List<CategoryModelClass>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error in Connection, Please try again", Toast.LENGTH_SHORT).show();

            }
        });


    }

    //on recyler item click listener
    @Override
    public void onItemClick(int position) {
        CategoryModelClass clickedItem=categorylist.get(position);
        String categoryId=String.valueOf(clickedItem.getCategory_id());
        Intent intent=new Intent(getContext(), DoctorListActivity.class);
        //pass these items to the next activity
        intent.putExtra("categoryId",categoryId);
        intent.putExtra("categoryName",clickedItem.getCategory_name());
        startActivity(intent);


    }
}