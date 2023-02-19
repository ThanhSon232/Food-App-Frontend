package com.example.myapplication.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.AdditionFood;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdditionFoodAdapter extends  RecyclerView.Adapter<AdditionFoodAdapter.AdditionFoodViewHolder>{
    List<AdditionFood> additionFoodList;

    public void setAdditionFoodList(List<AdditionFood> additionFoodList) {
        this.additionFoodList = additionFoodList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdditionFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_add, parent, false);

        return new AdditionFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionFoodViewHolder holder, int position) {
        AdditionFood additionFood = additionFoodList.get(position);
        System.out.println(additionFood);
        if(additionFood == null) return;
        if(additionFood.getImageURL().size() != 0){
            Picasso.get()
                    .load("http://192.168.100.3:8080/image/" + additionFood.getImageURL().get(0).getName()).error(R.drawable.ic_baseline_error_outline_24)
                    .into(holder.imageView);
        }
        holder.name.setText(additionFood.getName());
        holder.price.setText(String.valueOf(additionFood.getPrice()));

    }

    @Override
    public int getItemCount() {
        return additionFoodList.size();
    }

    class AdditionFoodViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        TextView name;
        TextView price;
        RadioButton radioButton;
        public AdditionFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.add_image);
            name = itemView.findViewById(R.id.add_name);
            price = itemView.findViewById(R.id.add_price);
            radioButton = itemView.findViewById(R.id.add_radio_button);

        }
    }
}
