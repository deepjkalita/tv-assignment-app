package com.example.tv_assignment_app.Adapters;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv_assignment_app.ModelClasses.ConsultationModelClass;
import com.example.tv_assignment_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.ExampleViewHolder> {
    private ArrayList<ConsultationModelClass> mExampleList;

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView doctorName;
        public TextView category;
        public ImageView doctorImage;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_name);
            category = itemView.findViewById(R.id.category);
            doctorImage=itemView.findViewById(R.id.doctor_image);
        }
    }

    public ConsultationAdapter(ArrayList<ConsultationModelClass> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.consultation_cardview, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ConsultationModelClass currentItem = mExampleList.get(position);
        holder.doctorName.setText(currentItem.getDoctor_name());
        holder.category.setText(currentItem.getCategory_name());
        Picasso.get()
                .load(currentItem.getDoctor_image())
                .into(holder.doctorImage);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}

