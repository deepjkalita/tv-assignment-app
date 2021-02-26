package com.example.tv_assignment_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv_assignment_app.ModelClasses.DoctorModelClass;
import com.example.tv_assignment_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ExampleViewHolder> {
    private ArrayList<DoctorModelClass> mExampleList;

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView doctorName;
        public TextView experience;
        public TextView qualification;
        public TextView price;
        public TextView availibility;
        public TextView jobRole;
        public ImageView doctorImage;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_name);
            experience = itemView.findViewById(R.id.experience);
            qualification = itemView.findViewById(R.id.qualification);
            price = itemView.findViewById(R.id.price);
            availibility = itemView.findViewById(R.id.availibilty);
            jobRole = itemView.findViewById(R.id.job_role);
            doctorImage=itemView.findViewById(R.id.doctor_image);
        }
    }
    public DoctorAdapter(ArrayList<DoctorModelClass> exampleList) {
        mExampleList = exampleList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.docotor_list_cardview, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        DoctorModelClass currentItem = mExampleList.get(position);
        holder.doctorName.setText(currentItem.getDoctor_name());
        holder.qualification.setText(currentItem.getDegree());
        holder.experience.setText(currentItem.getExperience());
        holder.price.setText(currentItem.getPrice());
        holder.availibility.setText(currentItem.getNext_available());
        holder.jobRole.setText(currentItem.getCategory_name());
        Picasso.get()
                .load(currentItem.getDoctor_image())
                .into(holder.doctorImage);
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}
