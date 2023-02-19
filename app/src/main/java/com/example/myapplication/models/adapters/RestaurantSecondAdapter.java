package com.example.myapplication.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Restaurant;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantSecondAdapter extends RecyclerView.Adapter<RestaurantSecondAdapter.RestaurantSecondViewHolder>{
    List<Restaurant> restaurantList;
    Context mContext;

    public RestaurantSecondAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantSecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent, false);
        return new RestaurantSecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantSecondViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        if(restaurant == null) return;
        if(restaurant.getImageURL().size() != 0) {
            Picasso.get()
                    .load("http://192.168.100.3:8080/image/" + restaurant.getImageURL().get(0).getName()).error(R.drawable.ic_baseline_error_outline_24)
                    .fit()
                    .into(holder.imageView);
        }
        holder.name.setText(restaurant.getName());
        holder.rateRatio.setText(String.valueOf(restaurant.getRateRatio()));
        holder.numRate.setText(String.valueOf("("+restaurant.getNumRate()+")"));

    }

    @Override
    public int getItemCount() {
        return restaurantList == null ? 0 : restaurantList.size();
    }

    class RestaurantSecondViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        TextView name;
        TextView price;
        TextView rateRatio;
        TextView numRate;
        TextView freeShipping;
        public RestaurantSecondViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image);
            name = itemView.findViewById(R.id.restaurant_name);
            price = itemView.findViewById(R.id.restaurant_price);
            rateRatio = itemView.findViewById(R.id.restaurant_rate_ratio);
            numRate = itemView.findViewById(R.id.restaurant_num_rate);
        }
    }
}
