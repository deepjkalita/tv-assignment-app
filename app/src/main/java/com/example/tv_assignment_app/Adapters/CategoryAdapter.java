package com.example.tv_assignment_app.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv_assignment_app.ModelClasses.CategoryModelClass;
import com.example.tv_assignment_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ExampleViewHolder> {
    private ArrayList<CategoryModelClass> mExampleList;
    private CategoryAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryTextView;
        public ImageView categoryImageView;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_name);
            categoryImageView=itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public CategoryAdapter(ArrayList<CategoryModelClass> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_cardview, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        CategoryModelClass currentItem = mExampleList.get(position);
        holder.categoryTextView.setText(currentItem.getCategory_name());
        Picasso.get()
                .load(currentItem.getCategory_image_url())
                .into(holder.categoryImageView);
        Picasso.get().setLoggingEnabled(true);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
