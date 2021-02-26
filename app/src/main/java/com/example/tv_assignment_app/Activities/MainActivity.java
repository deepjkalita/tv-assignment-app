package com.example.tv_assignment_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tv_assignment_app.Fragments.ConsultationDetailsFragment;
import com.example.tv_assignment_app.Fragments.HomeFragment;
import com.example.tv_assignment_app.ModelClasses.UserModelClass;
import com.example.tv_assignment_app.R;
import com.example.tv_assignment_app.apiServer.ApiCall;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    //interface for api calls declaration
    private ApiCall apiCall;
    Retrofit retrofit;

    //images for logos declaration
    private ImageView proPicImageView;
    private ImageView proPicBottomImageView;

    //userID of the person who is suppossed to be logged in
    private String userId="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
       //bottomNav.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_nav_gradient_background));
        proPicImageView=findViewById(R.id.profile_pic);
        proPicBottomImageView=findViewById(R.id.profile_pic_bottom);
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/")
                .build();

        getUserDetails();//function to get details of user from database

        //default starter fragment
        if (savedInstanceState == null) {
            Fragment selectedFragment = new HomeFragment(); //categories are dsiplayed here
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
        }

        //fragment call for home button
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = new ConsultationDetailsFragment();
                        Bundle bundle = new Bundle();
                        //user id passsed to the fragment
                        bundle.putString("userId", String.valueOf(userId));
                        selectedFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                        break;
                }
                return true;
            }
        });

    }

    private void getUserDetails() {
        apiCall = retrofit.create(ApiCall.class);
        Call<List<UserModelClass>> call = apiCall
                .getUser("http://assignmentserver-env.eba-e93fmkzt.ap-southeast-1.elasticbeanstalk.com/user/"+userId);
        call.enqueue(new Callback<List<UserModelClass>>() {
            @Override
            public void onResponse(Call<List<UserModelClass>> call, Response<List<UserModelClass>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<UserModelClass> users = response.body();
                for (UserModelClass user : users) {

                    Picasso.get()  //loading the images of logged user from server
                            .load(user.getUser_image())
                            .into(proPicImageView);
                    Picasso.get()
                            .load(user.getUser_image())
                            .into(proPicBottomImageView);
                }
            }
            @Override
            public void onFailure(Call<List<UserModelClass>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error in Connection, Please try again", Toast.LENGTH_SHORT).show();

            }
        });

    }


}